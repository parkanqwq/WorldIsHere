package com.kalabukhov.app.worldishere.ui

import com.kalabukhov.app.worldishere.domain.PersonModel

class EditPersonContract {

    enum class ViewState {
        LOADING, SUCCESS, ERROR, DEFEAT
    }

    interface View {
        fun setState(state: ViewState)
        fun setPerson(personModel: PersonModel)
        fun setAgeError(errorCode: Int)
    }

    interface Presenter {
        fun onAttach(view: View)
        fun onDetach()
        fun onFight(personModel: PersonModel)
        fun onCheckFight(personModel: PersonModel)
        fun onResultFight(resultFight: Int)
        fun onLimitedString(personModel: PersonModel) : Boolean
    }
}