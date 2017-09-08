package com.alexsullivan.griointerviewapp.github

import io.reactivex.Observable

class GithubNetworkRepository: GithubRepository {
    override fun loadRepositoryData(username: String): Observable<GithubUserRepo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}