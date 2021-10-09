package com.kalabukhov.app.worldishere.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalabukhov.app.worldishere.R
import com.kalabukhov.app.worldishere.databinding.ActivityMainBinding
import com.kalabukhov.app.worldishere.domain.PersonModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
    }

    companion object {
        private const val PERSON_EDIT_KEY = "person_key"

        fun createLauncherIntent(context: Context, personModel: PersonModel?) : Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(PERSON_EDIT_KEY, personModel)
            return intent
        }
    }
}

