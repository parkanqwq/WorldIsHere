package com.kalabukhov.app.worldishere.ui.main

import com.kalabukhov.app.worldishere.App
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd

class FightAmazonkaContract {

    enum class ViewState {
        IMHITTING, BOTHITTING, IMHEATH, BOTHEATH,
        IMPREPARATION, BOTPREPARATION, DEFEAT, WIN
    }

    interface View : MvpView {
        @AddToEnd
        fun setState(state: ViewState)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun onHit(app: App)
        abstract fun onHeath(app: App)
        abstract fun onWaiting(app: App)
        abstract fun onHitOrHeath()
        abstract fun onResultFight(app: App)
        abstract fun onFinishFight(boolean: Boolean)
    }
}