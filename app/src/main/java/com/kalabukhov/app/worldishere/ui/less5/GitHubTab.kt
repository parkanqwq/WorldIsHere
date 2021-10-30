package com.kalabukhov.app.worldishere.ui.less5

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kalabukhov.app.worldishere.R
import com.kalabukhov.app.worldishere.app
import com.kalabukhov.app.worldishere.databinding.ActivityGitHubTabBinding
import com.kalabukhov.app.worldishere.domain.GitHubRepoEntity
import com.kalabukhov.app.worldishere.ui.adapter.AdapterGitHubUsers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class GitHubTab : AppCompatActivity() {

    private lateinit var binding: ActivityGitHubTabBinding
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGitHubTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter

        binding.button.setOnClickListener {
            binding.nameUserEditText.text = binding.nameUserEditText.text
            disposable = app.gitHabUsersApi.listRepo(binding.nameUserEditText.text.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { repos ->
                        val gitHubUser: ArrayList<GitHubRepoEntity> = arrayListOf()
                        gitHubUser.add(repos)
                        adapter.setUsersGitHub(gitHubUser)
                    } ,
                    { thr ->
                        Toast.makeText(this, resources.getString(R.string.error)+
                                thr.message, Toast.LENGTH_SHORT).show()
                    })
        }
    }

    private val onObjectListener = object : OnItemViewClickListener {
        override fun onItemViewClick(gitHubRepoEntity: GitHubRepoEntity) {
            val intent = Intent(this@GitHubTab, GitHubUsersRepoActivity::class.java)
            intent.putExtra(resources.getString(R.string.nameEnglish), gitHubRepoEntity.name)
            startActivity(intent)
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(gitHubRepoEntity: GitHubRepoEntity)
    }

    private val adapter = AdapterGitHubUsers(onObjectListener)

    companion object {
        fun createLauncherIntentLesson5(context: Context): Intent {
            return Intent(context, GitHubTab::class.java)
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}