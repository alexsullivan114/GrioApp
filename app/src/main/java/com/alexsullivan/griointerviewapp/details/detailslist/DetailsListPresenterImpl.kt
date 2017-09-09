package com.alexsullivan.griointerviewapp.details.detailslist

import com.alexsullivan.griointerviewapp.github.GithubUser
import com.alexsullivan.griointerviewapp.github.GithubUserRepo

class DetailsListPresenterImpl(private val user: GithubUser): DetailsListPresenter() {
    override fun getUserRepos() = user.repos.sortedByDescending { it.stars }

    override fun repoClicked(repo: GithubUserRepo) {
        if (!user.name.isEmpty() && !repo.title.isEmpty()) {
            val url = "https://github.com/${user.name}/${repo.title}"
            view?.openRepoWebview(url)
        }
    }
}