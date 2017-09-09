package com.alexsullivan.griointerviewapp

import com.alexsullivan.griointerviewapp.github.GithubUser
import io.reactivex.Observable

abstract class StartViewAdapter: StartView {
    override fun showWinnerScreen(winner: GithubUser, loser: GithubUser) {}
    override fun showNetworkError() {}
    override fun showUserOneInputError() {}
    override fun showUserTwoInputError() {}
    override fun hideUserOneInputError() {}
    override fun hideUserTwoInputError() {}
    override fun getUserOneTextInputObservable() = Observable.empty<String>()
    override fun getUserTwoTextInputObservable() = Observable.empty<String>()
    override fun updateUserOneAvatar(url: String) {}
    override fun updateUserTwoAvatar(url: String) {}
}