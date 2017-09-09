package com.alexsullivan.griointerviewapp.details

import com.alexsullivan.griointerviewapp.baseview.Presenter
import com.alexsullivan.griointerviewapp.github.GithubUser

abstract class DetailsPresenter: Presenter<DetailsView>() {
    abstract fun provideUsers(): Pair<GithubUser, GithubUser>
}