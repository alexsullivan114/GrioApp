package com.alexsullivan.griointerviewapp.github.retrofit

import com.alexsullivan.griointerviewapp.github.GithubUser
import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit interface to interact with the Github api.
 */
interface GithubService {

    /**
     * Load a users repos.
     * @param id: The username of the user whos repos we're looking for.
     */
    @GET("users/{id}/repos")
    fun loadUsersRepos(@Path(value = "id", encoded = true) id: String): Observable<List<GithubUserRepo>>

    @GET("users/{id}")
    fun loadUserData(@Path(value = "id", encoded = true) id: String): Single<Response<GithubUser>>
}