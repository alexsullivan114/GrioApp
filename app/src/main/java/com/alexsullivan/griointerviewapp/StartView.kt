package com.alexsullivan.griointerviewapp

import com.alexsullivan.griointerviewapp.baseview.ViewInterface

interface StartView: ViewInterface {
    fun showWinnerScreen(winner: String)
    fun showNetworkError()
    fun showUserOneInputError()
    fun showUserTwoInputError()
}