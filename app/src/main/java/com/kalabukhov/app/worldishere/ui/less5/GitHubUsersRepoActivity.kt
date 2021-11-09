package com.kalabukhov.app.worldishere.ui.less5

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kalabukhov.app.worldishere.R
import com.kalabukhov.app.worldishere.app
import com.kalabukhov.app.worldishere.databinding.ActivityGitHubUsersRepoBinding
import com.kalabukhov.app.worldishere.domain.GitHubRepoUserEntity
import com.kalabukhov.app.worldishere.domain.GitHubRepoUserEntityDTO
import com.kalabukhov.app.worldishere.domain.UsersRepositoriesRepo
import com.kalabukhov.app.worldishere.domain.entity.UserRepositoriesEntity
import com.kalabukhov.app.worldishere.rest.GitHubApi
import com.kalabukhov.app.worldishere.rest.GitHubApiRepo
import com.kalabukhov.app.worldishere.ui.adapter.AdapterGitHubUsersRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.wait
import org.koin.android.ext.android.inject
import java.util.*
import javax.inject.Inject

class GitHubUsersRepoActivity : AppCompatActivity() {

    private var disposable: CompositeDisposable = CompositeDisposable()
    private lateinit var binding: ActivityGitHubUsersRepoBinding
    private val adapter = AdapterGitHubUsersRepo()


    //ниже коин, все пучком работат и коин и дагер
    //private val gitHabUsersApiRepo: GitHubApiRepo by inject()
    //private val userRepositoriesRepo: UsersRepositoriesRepo by inject()

    @Inject
    lateinit var gitHabUsersApiRepo: GitHubApiRepo

    @Inject
    lateinit var userRepositoriesRepo: UsersRepositoriesRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGitHubUsersRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app.appComponent.inject(this)

        binding.repoRecyclerView.adapter = adapter
        disposableForUsers()
        loadingUsersWeb()
    }

    private fun loadingRepositoriesRoom() {
        //обновляем данные в адаптаре из рума, который получил данные с сервера
        //но если данные не обновленны, точнее пока они обновляются, приложение
        // показывает старый список из руума
        userRepositoriesRepo.userRepositories
            .subscribe ({ repos ->
                adapter.setUsersGitHub(repos)
            },
                { thr ->
                    Toast.makeText(this, resources.getString(R.string.error)+
                            thr.message, Toast.LENGTH_SHORT).show()
                })
    }

    private fun loadingUsersWeb() {
        //загружаем репозитории с сервера
        disposable.add(gitHabUsersApiRepo.listRepo(intent.getStringExtra(
            resources.getString(R.string.nameEnglish))!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repos ->
                    //добавляем все данные в руум
                    loadInRoomUsers(repos)
                } ,
                { thr ->
                    Toast.makeText(this@GitHubUsersRepoActivity,
                        resources.getString(R.string.error) +
                                thr.message, Toast.LENGTH_SHORT).show()
                }))
    }

    private fun disposableForUsers() {
        //подписываемся чтоб обновлять даные и показывать актуальные репозитории
        disposable.add(
            userRepositoriesRepo.userRepositories
                .subscribe {
                    loadingRepositoriesRoom()
                }
        )
    }

    private fun loadInRoomUsers(repos: List<GitHubRepoUserEntity>) {
        userRepositoriesRepo.clear()
        //преобразуем из одного власса в другой
        for (userRepositories in repos) {
            userRepositoriesRepo.put(UserRepositoriesEntity(
                UUID.randomUUID().toString(),
                userRepositories.name.toString(),
                userRepositories.owner.avatarUrl
            ))
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}