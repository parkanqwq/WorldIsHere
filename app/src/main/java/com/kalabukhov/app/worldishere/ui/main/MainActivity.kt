package com.kalabukhov.app.worldishere.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kalabukhov.app.worldishere.R
import com.kalabukhov.app.worldishere.databinding.ActivityMainBinding
import com.kalabukhov.app.worldishere.domain.PersonModel
import com.kalabukhov.app.worldishere.domain.PersonRepo
import com.kalabukhov.app.worldishere.impl.PersonRepoImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val personRepo: PersonRepo = PersonRepoImpl()
//    private var currentDisposable: Disposable? = null
//    set(value) {
//        field?.takeIf { !it.isDisposed }?.dispose()
//        field = value
//    }
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding){
        val personModel = intent.getSerializableExtra(PERSON_EDIT_KEY) as? PersonModel
        nameProfile.text = resources.getString(R.string.text_hi) + personModel?.name
        agePerson.text = resources.getString(R.string.text_age_person) + personModel?.age.toString()
        powerPerson.text = resources.getString(R.string.text_power_person) + personModel?.power.toString()

        buttonChangeName.setOnClickListener {
            val name = editTextNewName.text.toString()
            if (name.isNotBlank()) {
                personRepo.change(name)
            } else Toast.makeText(this@MainActivity, R.string.strings_is_empty, Toast.LENGTH_SHORT).show()
        }
//        currentDisposable = personRepo.person.subscribe {
//            nameProfile.text = "$it 1, "
//        }
        compositeDisposable.add(
            personRepo.person
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { listPerson -> nameProfile.text = resources.getString(R.string.text_hi) + listPerson },
                    { thr -> Toast.makeText(this@MainActivity, thr.message, Toast.LENGTH_SHORT).show() }
                )
        )
    }

    companion object {
        private const val PERSON_EDIT_KEY = "person_key"

        fun createLauncherIntent(context: Context, personModel: PersonModel?) : Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(PERSON_EDIT_KEY, personModel)
            return intent
        }
    }

    override fun onDestroy() {
//        currentDisposable = null
        compositeDisposable.dispose()
        super.onDestroy()
    }
}

