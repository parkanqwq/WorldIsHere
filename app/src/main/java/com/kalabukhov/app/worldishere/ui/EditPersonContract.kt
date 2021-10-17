package com.kalabukhov.app.worldishere.ui

import com.kalabukhov.app.worldishere.domain.PersonModel
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

class EditPersonContract {

    enum class ViewState {
        LOADING, SUCCESS, ERROR, DEFEAT
    }

    interface View : MvpView {
        @AddToEnd
        fun setState(state: ViewState)
        @AddToEnd
        fun setPerson(personModel: PersonModel)
        @AddToEndSingle
        fun setAgeError(errorCode: Int)
        // самодельный класс cicerone
//        @Skip
//        fun exit()
//        @Skip
//        fun openMainScreen(personModel: PersonModel?)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun onFight(personModel: PersonModel)
        abstract fun onCheckFight(personModel: PersonModel)
        abstract fun onResultFight(resultFight: Int, personModel: PersonModel)
        abstract fun onLimitedString(personModel: PersonModel) : Boolean
    }
}