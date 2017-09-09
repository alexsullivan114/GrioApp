package com.alexsullivan.griointerviewapp

import com.alexsullivan.griointerviewapp.baseview.ViewInterface
import com.alexsullivan.griointerviewapp.github.GithubUser
import io.reactivex.Observable

interface StartView: ViewInterface {
    fun showWinnerScreen(winner: GithubUser, loser: GithubUser)
    fun showNetworkError()
    fun showUserOneInputError()
    fun showUserTwoInputError()
    fun hideUserOneInputError()
    fun hideUserTwoInputError()
    fun getUserOneTextInputObservable(): Observable<String>
    fun getUserTwoTextInputObservable(): Observable<String>
    fun updateUserOneAvatar(url: String)
    fun updateUserTwoAvatar(url: String)
}