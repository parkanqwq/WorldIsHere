package com.kalabukhov.app.worldishere.ui.main

import android.os.Handler
import android.os.Looper
import com.github.terrakok.cicerone.Router
import com.kalabukhov.app.worldishere.bus.AttackEvent
import com.kalabukhov.app.worldishere.bus.EventBus
import com.kalabukhov.app.worldishere.bus.HealthEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.java.KoinJavaComponent.inject

class FightAmazonkaPresenter(
    private val router: Router
) : FightAmazonkaContract.Presenter() {

    private val imAttackBus: EventBus by inject(EventBus::class.java)
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var beginFight = true
    private var theAnd = true

    override fun onHit() {
        imAttackBus.post(AttackEvent())
        onWaiting()
    }

    override fun onHeath() {
        imAttackBus.post(HealthEvent())
        onWaiting()
    }

    override fun onWaiting() {
        viewState.setState(FightAmazonkaContract.ViewState.IMPREPARATION)
        onResultFight()
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

    override fun onResultFight() {
        if (beginFight) {
            beginFight = false
            compositeDisposable.add(imAttackBus.get()
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