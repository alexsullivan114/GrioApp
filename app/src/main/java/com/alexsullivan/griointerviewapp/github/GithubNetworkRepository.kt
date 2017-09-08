package com.alexsullivan.griointerviewapp.github

import com.alexsullivan.griointerviewapp.github.retrofit.GithubService
import com.alexsullivan.griointerviewapp.github.serialization.GithubUserRepoDeserializer
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GithubNetworkRepository(private val service: GithubService): GithubRepository {

    companion object {
        fun build(): GithubNetworkRepository {
            val gson = GsonBuilder().registerTypeAdapter(GithubUserRepo::class.java, GithubUserRepoDeserializer()).create()
            val client = OkHttpClient()
            val service = Retrofit.Builder().client(client)
                .baseUrl(GithubService.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(GithubService::class.java)

            return GithubNetworkRepository(service)
        }
    }

    override fun loadRepositoryData(username: String) =
        service.loadUsersRepos(username).flatMap { Observable.fromIterable(it) }

}