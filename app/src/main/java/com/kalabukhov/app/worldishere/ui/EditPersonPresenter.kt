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
            {onCheckFight(personModel)}, ONE_SECOND)
    }

    override fun onCheckFight(personModel: PersonModel) {
        var bestPower = IM_WIN
        for (botBestPower in personsGames.getAllPerson()) {
            if (botBestPower.power >= personModel.power) bestPower = BOT_WIN
        }
        onResultFight(bestPower)
    }

    override fun onResultFight(resultFight: Int) {
        if (resultFight == IM_WIN) view?.setState(EditPersonContract.ViewState.SUCCESS)
        else view?.setState(EditPersonContract.ViewState.DEFEAT)
    }

    override fun onLimitedString(personModel: PersonModel): Boolean {
        if (personModel.name.length > LENGTH_STRING || personModel.age > LENGTH_AGE || personModel.power > LENGTH_POWER) {
            view?.setState(EditPersonContract.ViewState.ERROR)
            return false
        }
        return true
    }

    companion object {
        const val IM_WIN = 0
        const val BOT_WIN = 1
        const val LENGTH_STRING = 10
        const val LENGTH_AGE = 1000
        const val LENGTH_POWER = 100
        const val ONE_SECOND = 1000L
    }
}