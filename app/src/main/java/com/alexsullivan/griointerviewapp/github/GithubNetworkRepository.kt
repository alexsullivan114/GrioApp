package com.alexsullivan.griointerviewapp.github

import com.alexsullivan.griointerviewapp.github.retrofit.GithubService
import com.alexsullivan.griointerviewapp.github.serialization.GithubUserDeserializer
import com.alexsullivan.griointerviewapp.github.serialization.GithubUserRepoDeserializer
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * An implementation of GithubRepository that fetches github information from the network.
 */
class GithubNetworkRepository(private val service: GithubService): GithubRepository {

    companion object {
        fun build(): GithubNetworkRepository {
            val gson = GsonBuilder()
                .registerTypeAdapter(GithubUserRepo::class.java, GithubUserRepoDeserializer())
                .registerTypeAdapter(GithubUser::class.java, GithubUserDeserializer())
                .create()
            val client = OkHttpClient()
            val service = Retrofit.Builder().client(client)
                .baseUrl(GithubService.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(GithubService::class.java)

            return GithubNetworkRepository(service)
        }
    }

    private fun loadRepositoryData(username: String) = service.loadUsersRepos(username)

    private fun loadUserDataInternal(username: String): Observable<GithubUser> {
        return service.loadUserData(username)
            .flatMapObservable {
                if (it.isSuccessful) {
                    Observable.just(it.body())
                } else {
                    Observable.empty()
                }
            }
    }

    /**
     * Loads some user data from the github api. NOTE: This method **does not** populate
     * repo data. A call to loadRepositoryData needs to be made for that. If the user is not found
     * then an empty observable will be returned.
     */
    override fun loadUserData(username: String): Observable<GithubUser> {
        return loadUserDataInternal(username)
            .zipWith(loadRepositoryData(username), BiFunction { t1, t2 ->
                t1.copy(repos = t2)
            })
    }
}