package com.alexsullivan.griointerviewapp

import com.alexsullivan.griointerviewapp.baseview.Presenter

abstract class StartPresenter: Presenter<StartView>() {
    abstract fun startClicked()
    abstract fun firstUserAvatarClicked()
    abstract fun secondUserAvatarClicked()
}