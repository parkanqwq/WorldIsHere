package com.kalabukhov.app.worldishere.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.kalabukhov.app.worldishere.R
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
                    if (agePerson.text.toString().toInt() > BIG_AGE) setAgeError(ERROR_AGE)
                }
            }
        }
    }

    private fun gatherPeron(): PersonModel? = with(binding) {
        if (checkTheIsEmptyEdit()) {
            return PersonModel(ID,
                namePerson.text.toString(),
                agePerson.text.toString().toInt(),
                powerPerson.text.toString().toInt()
            )
        }
        Snackbar.make(root, R.string.strings_is_empty, Snackbar.LENGTH_SHORT).show()
        return null
    }

    private fun checkTheIsEmptyEdit() = with(binding) {
        namePerson.text.toString().isNotEmpty() &&
            agePerson.text.toString().isNotEmpty() &&
            powerPerson.text.toString().isNotEmpty()
    }

    override fun setState(state: EditPersonContract.ViewState) = with(binding) {
        when (state) {
            EditPersonContract.ViewState.LOADING -> {
                layoutInfoPerson.setBackgroundColor(Color.parseColor(COLOR_LOADING))
                progressBar.visibility = View.VISIBLE
            }
            EditPersonContract.ViewState.SUCCESS -> {
                stopLoadingActions()
                Snackbar.make(root, R.string.im_win, Snackbar.LENGTH_SHORT).show()
            }
            EditPersonContract.ViewState.ERROR -> {
                stopLoadingActions()
                Snackbar.make(root, R.string.error_limited, Snackbar.LENGTH_SHORT).show()
            }
            EditPersonContract.ViewState.DEFEAT -> {
                stopLoadingActions()
                Snackbar.make(root, R.string.you_lose, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun stopLoadingActions() = with(binding) {
        layoutInfoPerson.setBackgroundColor(Color.WHITE)
        progressBar.visibility = View.GONE
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
        var errorAge: String = resources.getString(R.string.error)
        if (errorCode == BIG_AGE_BY_CODE) errorAge = resources.getString(R.string.error_big_age)
        return errorAge
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    companion object {
        const val ID = 0
        const val ERROR_AGE = 1
        const val BIG_AGE = 110
        const val BIG_AGE_BY_CODE = 1
        const val COLOR_LOADING = "#65BB86FC"
    }
}