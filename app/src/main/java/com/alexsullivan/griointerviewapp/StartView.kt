package com.alexsullivan.griointerviewapp

import com.alexsullivan.griointerviewapp.baseview.ViewInterface
import com.alexsullivan.griointerviewapp.github.GithubUser

interface StartView: ViewInterface {
    fun showWinnerScreen(winner: GithubUser, loser: GithubUser)
    fun showNetworkError()
    fun showUserOneInputError()
    fun showUserTwoInputError()
}