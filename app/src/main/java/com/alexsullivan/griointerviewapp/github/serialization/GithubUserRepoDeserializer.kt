package com.alexsullivan.griointerviewapp.github.serialization

import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class GithubUserRepoDeserializer: JsonDeserializer<GithubUserRepo> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): GithubUserRepo {
        val name = json.asJsonObject.get("name")?.asString ?: throw IllegalStateException("Missing name attribute!")
        val stars = json.asJsonObject.get("stargazers_count")?.asInt ?: 0
        return GithubUserRepo(name, stars)
    }
}