package com.alexsullivan.griointerviewapp.github.retrofit

import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit interface to interact with the Github api.
 */
interface GithubService {
    companion object {
        val baseUrl = "https://api.github.com/"
    }

    /**
     * Load a users repos.
     * @param id: The username of the user whos repos we're looking for.
     */
    @GET("users/{id}/repos")
    fun loadUsersRepos(@Path(value = "id", encoded = true) id: String): Observable<List<GithubUserRepo>>
}