package com.kalabukhov.app.worldishere.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.kalabukhov.app.worldishere.R
import com.kalabukhov.app.worldishere.app
import com.kalabukhov.app.worldishere.bus.*
import com.kalabukhov.app.worldishere.databinding.ActivityMainBinding
import com.kalabukhov.app.worldishere.domain.PersonModel
import com.kalabukhov.app.worldishere.domain.PersonRepo
import com.kalabukhov.app.worldishere.impl.PersonRepoImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : MvpAppCompatActivity(), FightAmazonkaContract.View {

    private val fightAmazonkaPresenter: FightAmazonkaPresenter by inject()
    private val presenter by moxyPresenter { fightAmazonkaPresenter }

    private lateinit var binding: ActivityMainBinding
    private val personRepo: PersonRepo = PersonRepoImpl()

//    private var currentDisposable: Disposable? = null
//    set(value) {
//        field?.takeIf { !it.isDisposed }?.dispose()
//        field = value
//    }

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var hpIm = 100
    private var hpBot = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        btnOff()
        goFightGames()
    }

    override fun setState(state: FightAmazonkaContract.ViewState) = with(binding){
        when (state) {
            FightAmazonkaContract.ViewState.IMHEATH -> {
                hpIm += randomHitOrHeath()
                hpImProgressBar.setProgress(hpIm, true)
            }
            FightAmazonkaContract.ViewState.IMHITTING -> {
                val randomHitOrHeath = hpBot
                hpBot -= randomHitOrHeath()
                botMinHp(randomHitOrHeath - hpBot)
                hpAmazonkaProgressBar.setProgress(hpBot, true)
            }
            FightAmazonkaContract.ViewState.BOTHEATH -> {
                hpBot += randomHitOrHeath()
                hpAmazonkaProgressBar.setProgress(hpBot, true)
            }
            FightAmazonkaContract.ViewState.BOTHITTING -> {
                val randomHitOrHeath = hpIm
                hpIm -= randomHitOrHeath()
                imMinHp(randomHitOrHeath - hpIm)
                hpImProgressBar.setProgress(hpIm, true)
            }
            FightAmazonkaContract.ViewState.IMPREPARATION -> {
                btnOff()
                finishFight()
            }
            FightAmazonkaContract.ViewState.BOTPREPARATION -> {
                btnOn()
            }
            FightAmazonkaContract.ViewState.DEFEAT -> {
                btnOff()
                textMinusHpIm.text = "СМЕРТЬ!"
                minusHpImLinearLayout.visibility = View.VISIBLE
            }
            FightAmazonkaContract.ViewState.WIN -> {
                btnOff()
                textMinusHpAmazonka.text = "СМЕРТЬ"
                minusHpBotLinearLayout.visibility = View.VISIBLE
            }
        }
    }

    fun finishFight() {
        if (hpIm <= 0)
            presenter.onFinishFight(false)
        if (hpBot <= 0)
            presenter.onFinishFight(true)
    }

    private fun imMinHp(hpIm: Int) = with(binding) {
        textMinusHpIm.text = "-" + hpIm.toString()
        minusHpImLinearLayout.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            minusHpImLinearLayout.visibility = View.GONE
        }, 500L)
    }

    private fun botMinHp(hpBot: Int) = with(binding) {
        textMinusHpAmazonka.text = "-" + hpBot.toString()
        minusHpBotLinearLayout.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            minusHpBotLinearLayout.visibility = View.GONE
        }, 500L)
    }

    private fun randomHitOrHeath(): Int {
        return (10..25).random()
    }

    private fun goFightGames() {
        binding.goGames.setOnClickListener {
            presenter.onHit()
            binding.goGames.isEnabled = false
            btnOn()
        }
    }

    private fun btnOff() = with(binding){
        fightBtn.isEnabled = false
        fightBtn.setBackgroundColor(Color.parseColor("#4C4949"))
        minusPowerBtn.isEnabled = false
        minusPowerBtn.setBackgroundColor(Color.parseColor("#4C4949"))
    }

    private fun btnOn() = with(binding){
        fightBtn.isEnabled = true
        fightBtn.setBackgroundColor(Color.parseColor("#66BB6A"))
        minusPowerBtn.isEnabled = true
        minusPowerBtn.setBackgroundColor(Color.parseColor("#66BB6A"))
    }

    private fun initView() = with(binding){
        val personModel = intent.getSerializableExtra(PERSON_EDIT_KEY) as? PersonModel
        nameProfile.text = resources.getString(R.string.text_hi) + personModel?.name
        agePerson.text = resources.getString(R.string.text_age_person) + personModel?.age.toString()
        powerPerson.text = resources.getString(R.string.text_power_person) + personModel?.power.toString()

        buttonChangeName.setOnClickListener {
            changeName()
        }
        compositeDisposableFun()
        eventForPower()
    }

    private fun eventForPower() = with(binding){
        fightBtn.setOnClickListener {
            presenter.onHit()
        }
        minusPowerBtn.setOnClickListener {
            presenter.onHeath()
        }
    }

    private fun compositeDisposableFun() {
        compositeDisposable.add(
            personRepo.person
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { listPerson -> binding.nameProfile.text =
                        resources.getString(R.string.text_hi) + listPerson },
                    { thr -> Toast.makeText(this@MainActivity, thr.message,
                        Toast.LENGTH_SHORT).show() }
                )
        )
    }

    private fun changeName() {
        val name = binding.editTextNewName.text.toString()
        if (name.isNotBlank()) {
            personRepo.change(name)
        } else Toast.makeText(this@MainActivity, R.string.strings_is_empty,
            Toast.LENGTH_SHORT).show()
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
        compositeDisposable.dispose()
        super.onDestroy()
    }
}

