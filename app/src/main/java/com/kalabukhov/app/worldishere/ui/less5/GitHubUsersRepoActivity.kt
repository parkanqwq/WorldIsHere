package com.kalabukhov.app.worldishere.ui.less5

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kalabukhov.app.worldishere.R
import com.kalabukhov.app.worldishere.app
import com.kalabukhov.app.worldishere.databinding.ActivityGitHubUsersRepoBinding
import com.kalabukhov.app.worldishere.ui.adapter.AdapterGitHubUsersRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class GitHubUsersRepoActivity : AppCompatActivity() {

    private var disposable: CompositeDisposable = CompositeDisposable()
    private lateinit var binding: ActivityGitHubUsersRepoBinding
    private val adapter = AdapterGitHubUsersRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGitHubUsersRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.repoRecyclerView.adapter = adapter
        disposable.add(app.gitHabUsersApiRepo.listRepo(intent.getStringExtra(
            resources.getString(R.string.nameEnglish))!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repos ->
                    adapter.setUsersGitHub(repos)
                } ,
                { thr ->
                    Toast.makeText(this@GitHubUsersRepoActivity,
                        resources.getString(R.string.error) +
                            thr.message, Toast.LENGTH_SHORT).show()
                }))
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}