package com.alexsullivan.griointerviewapp.github

import io.reactivex.Observable

/**
 * Contract for loading github user repo data.
 */
interface GithubRepository {
    fun loadUserData(username: String): Observable<GithubUser>
}