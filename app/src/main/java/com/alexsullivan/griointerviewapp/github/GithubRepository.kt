package com.alexsullivan.griointerviewapp.github

import io.reactivex.Observable

/**
 * Contract for loading github user repo data.
 */
interface GithubRepository {
    fun loadRepositoryData(username: String): Observable<GithubUserRepo>
}