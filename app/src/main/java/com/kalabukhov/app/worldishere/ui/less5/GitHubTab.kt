package com.kalabukhov.app.worldishere.ui.less5

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kalabukhov.app.worldishere.R
import com.kalabukhov.app.worldishere.domain.entity.UserEntity
import com.kalabukhov.app.worldishere.databinding.ActivityGitHubTabBinding
import com.kalabukhov.app.worldishere.domain.GitHubRepoEntity
import com.kalabukhov.app.worldishere.domain.UsersRepo
import com.kalabukhov.app.worldishere.rest.GitHubApi
import com.kalabukhov.app.worldishere.ui.adapter.AdapterRoomUsers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import java.util.*

class GitHubTab : AppCompatActivity() {

    private lateinit var binding: ActivityGitHubTabBinding
    private var disposable: CompositeDisposable = CompositeDisposable()
//    private val userRepo by lazy { app.userRepo }
    private val userRepo: UsersRepo by inject()
    private val gitHabUsersApi: GitHubApi by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGitHubTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapterRoom
        binding.findUserButtonView.setOnClickListener {
            getUsersGitHub()
        }

        disposableForUsers()
        clearBase()
    }

    private fun getUsersGitHub() {
        binding.nameUserEditText.text = binding.nameUserEditText.text
        disposable.add(gitHabUsersApi.listRepo(binding.nameUserEditText.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repos ->
                    addDbUsers(repos)
                } ,
                { thr ->
                    Toast.makeText(this, resources.getString(R.string.error)+
                            thr.message, Toast.LENGTH_SHORT).show()
                }))


    }

    private fun disposableForUsers() {
        disposable.add(
            userRepo.user
                .subscribe {
                    historyUsers()
                }
        )
    }

    private fun addDbUsers(gitHubRepoEntity: GitHubRepoEntity) {
        userRepo.put(
            UserEntity(UUID.randomUUID().toString(),
                gitHubRepoEntity.name.toString(),
                gitHubRepoEntity.avatarUrl.toString())
        )
//            .subscribeOn(Schedulers.io())
//            .subscribe()
//            .autoDisposable()
    }

    private fun historyUsers() {
        userRepo.user
            .subscribe({ repos ->
                adapterRoom.setUsersGitHubRoom(repos.asReversed())
            },
                { thr ->
                    Toast.makeText(this, resources.getString(R.string.error)+
                            thr.message, Toast.LENGTH_SHORT).show()
                })
    }

    private fun clearBase() {
        binding.clearButtonView.setOnClickListener {
            userRepo.clear()
        }
    }

//    private fun Completable.toLoadData(): Single<List<UserEntity>> {
//        return this
//            .andThen(userDao.getGitHubUsers())
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnError {
//                Toast.makeText(this@GitHubTab, resources.getString(R.string.error)+
//                        it.message, Toast.LENGTH_SHORT).show()
//            }
//    }

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

    private val adapterRoom = AdapterRoomUsers(onObjectListener)

    companion object {
        fun createLauncherIntentLesson5(context: Context): Intent {
            return Intent(context, GitHubTab::class.java)
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    private fun Disposable.autoDisposable() {
        disposable.add(this)
    }
}