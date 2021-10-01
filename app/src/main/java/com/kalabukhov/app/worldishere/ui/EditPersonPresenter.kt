package com.kalabukhov.app.worldishere.ui

import android.os.Handler
import android.os.Looper
import com.kalabukhov.app.worldishere.domain.PersonModel
import com.kalabukhov.app.worldishere.impl.PersonDB

class EditPersonPresenter : EditPersonContract.Presenter{

    private var view: EditPersonContract.View? = null
    private var personModel: PersonModel? = null
    private var personsGames: PersonDB = PersonDB()

    override fun onAttach(view: EditPersonContract.View) {
        this.view = view
        personModel?.let {
            view.setPerson(it)
        }
    }

    override fun onDetach() {
        this.view = null
    }

    override fun onFight(personModel: PersonModel) {
        view?.setState(EditPersonContract.ViewState.LOADING)
        Handler(Looper.getMainLooper()).postDelayed(
            {onCheckFight(personModel)}, 1000)
    }

    override fun onCheckFight(personModel: PersonModel) {
        var bestPower = 0
        for (botBestPower in personsGames.getAllPerson()) {
            if (botBestPower.power >= personModel.power) bestPower = 1
        }
        onResultFight(bestPower)
    }

    override fun onResultFight(resultFight: Int) {
        if (resultFight == 0) view?.setState(EditPersonContract.ViewState.SUCCESS)
        else view?.setState(EditPersonContract.ViewState.DEFEAT)
    }

    override fun onLimitedString(personModel: PersonModel): Boolean {
        if (personModel.name.length > 10 || personModel.age > 1000 || personModel.power > 100) {
            view?.setState(EditPersonContract.ViewState.ERROR)
            return false
        }
        return true
    }

    override fun onChangeAge(age: Int) {
        view?.setAgeError(1)
    }

}