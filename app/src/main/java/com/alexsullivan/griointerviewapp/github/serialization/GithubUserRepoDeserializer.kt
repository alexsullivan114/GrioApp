package com.alexsullivan.griointerviewapp.github.serialization

import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class GithubUserRepoDeserializer: JsonDeserializer<GithubUserRepo> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): GithubUserRepo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}