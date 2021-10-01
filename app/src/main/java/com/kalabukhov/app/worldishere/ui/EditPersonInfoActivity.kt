package com.kalabukhov.app.worldishere.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.kalabukhov.app.worldishere.databinding.ActivityEditPersonBinding
import com.kalabukhov.app.worldishere.domain.PersonModel

class EditPersonInfoActivity : AppCompatActivity(), EditPersonContract.View {

    private var presenter: EditPersonContract.Presenter = EditPersonPresenter()

    private lateinit var binding: ActivityEditPersonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.onAttach(this)
        initView()
    }

    private fun initView() = with(binding){
        btnRegistration.setOnClickListener {
            gatherPeron()?.let { personModel ->
                if (presenter.onLimitedString(personModel)) {
                    presenter.onFight(personModel)
                    if (agePerson.text.toString().toInt() > 110) setAgeError(1)
                }
            }
        }
    }

    private fun gatherPeron(): PersonModel? = with(binding) {
        if (checkTheNullEdit()) {
            return PersonModel(0,
                namePerson.text.toString(),
                agePerson.text.toString().toInt(),
                powerPerson.text.toString().toInt()
            )
        }
        Snackbar.make(root, "Заполните все данные", Snackbar.LENGTH_SHORT).show()
        return null
    }

    private fun checkTheNullEdit(): Boolean = with(binding) {
        if (namePerson.text.toString() != "" &&
            agePerson.text.toString() != "" &&
            powerPerson.text.toString() != "")
                return true
        else false
    }

    override fun setState(state: EditPersonContract.ViewState) = with(binding) {
        when (state) {
            EditPersonContract.ViewState.LOADING -> {
                layoutInfoPerson.setBackgroundColor(Color.parseColor("#65BB86FC"))
                progressBar.visibility = View.VISIBLE
            }
            EditPersonContract.ViewState.SUCCESS -> {
                layoutInfoPerson.setBackgroundColor(Color.WHITE)
                progressBar.visibility = View.GONE
                Snackbar.make(root, "Победа над врагами", Snackbar.LENGTH_SHORT).show()
            }
            EditPersonContract.ViewState.ERROR -> {
                layoutInfoPerson.setBackgroundColor(Color.WHITE)
                progressBar.visibility = View.GONE
                Snackbar.make(root, "Ошибка: превышены значения", Snackbar.LENGTH_SHORT).show()
            }
            EditPersonContract.ViewState.DEFEAT -> {
                layoutInfoPerson.setBackgroundColor(Color.WHITE)
                progressBar.visibility = View.GONE
                Snackbar.make(root, "Вы проиграли", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun setPerson(personModel: PersonModel) = with(binding) {
        namePerson.setText(personModel.name)
        agePerson.setText(personModel.age.toString())
        powerPerson.setText(personModel.power.toString())
    }

    override fun setAgeError(errorCode: Int) {
        binding.agePerson.error = getErrorAgeByCode(errorCode)
    }

    private fun getErrorAgeByCode(errorCode: Int) : String {
        var errorAge = "error"
        if (errorCode == 1) errorAge = "Недопустимый возраст, слишком большой"
        return errorAge
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}