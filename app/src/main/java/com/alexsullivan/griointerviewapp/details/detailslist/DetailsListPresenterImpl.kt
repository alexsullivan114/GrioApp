package com.alexsullivan.griointerviewapp.details.detailslist

import com.alexsullivan.griointerviewapp.github.GithubUser
import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import com.alexsullivan.griointerviewapp.github.buildGithubRepoUrl

class DetailsListPresenterImpl(private val user: GithubUser): DetailsListPresenter() {
    override fun getUserRepos() = user.repos.sortedByDescending { it.stars }


    override fun repoClicked(repo: GithubUserRepo) {
        if (!user.name.isEmpty() && !repo.title.isEmpty()) {
            view?.openRepoWebview(buildGithubRepoUrl(user.name, repo.title))
        }
    }
}