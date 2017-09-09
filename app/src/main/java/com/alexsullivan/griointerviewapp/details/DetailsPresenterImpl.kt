package com.alexsullivan.griointerviewapp.details

import com.alexsullivan.griointerviewapp.github.GithubUser

class DetailsPresenterImpl(private val winner: GithubUser,
                           private val loser: GithubUser) : DetailsPresenter() {

    override fun provideUsers() = winner to loser
}