package com.alexsullivan.griointerviewapp.details.detailslist

import com.alexsullivan.griointerviewapp.baseview.Presenter
import com.alexsullivan.griointerviewapp.github.GithubUserRepo

abstract class DetailsListPresenter: Presenter<DetailsListView>() {
    abstract fun getUserRepos(): List<GithubUserRepo>
    abstract fun repoClicked(repo: GithubUserRepo)
}