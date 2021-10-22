package com.kalabukhov.app.worldishere.ui.main

import android.os.Handler
import android.os.Looper
import com.github.terrakok.cicerone.Router
import com.kalabukhov.app.worldishere.App
import com.kalabukhov.app.worldishere.bus.AttackEvent
import com.kalabukhov.app.worldishere.bus.HealthEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class FightAmazonkaPresenter(
    private val router: Router
) : FightAmazonkaContract.Presenter() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var beginFight = true
    private var theAnd = true

    override fun onHit(app: App) {
        app.imAttackBus.post(AttackEvent())
        onWaiting(app)
    }

    override fun onHeath(app: App) {
        app.imAttackBus.post(HealthEvent())
        onWaiting(app)
    }

    override fun onWaiting(app: App) {
        viewState.setState(FightAmazonkaContract.ViewState.IMPREPARATION)
        onResultFight(app)
    }

    override fun onHitOrHeath() {
        if (theAnd) {
            val randomHitOrHeath = (0..1).random()
            if (randomHitOrHeath == 1)
                Handler(Looper.getMainLooper()).postDelayed({
                    viewState.setState(FightAmazonkaContract.ViewState.BOTHITTING)
                    viewState.setState(FightAmazonkaContract.ViewState.BOTPREPARATION)
                }, 1000L)
            else
                Handler(Looper.getMainLooper()).postDelayed({
                    viewState.setState(FightAmazonkaContract.ViewState.BOTHEATH)
                    viewState.setState(FightAmazonkaContract.ViewState.BOTPREPARATION)
                }, 1000L)
        }
    }

    override fun onResultFight(app: App) {
        if (beginFight) {
            beginFight = false
            compositeDisposable.add(app.imAttackBus.get()
//            .doOnNext{
//              // действия поток 1 (main)
//            }
//            .observeOn(Schedulers.computation())
//            .doOnNext{
//                // действия поток 2
//            }
//            .observeOn(Schedulers.io())
//            .doOnNext{
//                // действия поток 3
//            }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    // действия поток 1 (main)
                    if (it is AttackEvent) {
                        onHitOrHeath()
                        viewState.setState(FightAmazonkaContract.ViewState.IMHITTING)
                    } else if (it is HealthEvent) {
                        onHitOrHeath()
                        viewState.setState(FightAmazonkaContract.ViewState.IMHEATH)
                    }
                })
        }
    }

    override fun onFinishFight(boolean: Boolean) {
        compositeDisposable.dispose()
        theAnd = false
        if (boolean) {
            viewState.setState(FightAmazonkaContract.ViewState.WIN)
        }
        else {
            viewState.setState(FightAmazonkaContract.ViewState.DEFEAT)
        }
    }
}