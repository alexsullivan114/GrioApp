package com.alexsullivan.griointerviewapp.github.serialization

import com.alexsullivan.griointerviewapp.github.GithubUser
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class GithubUserDeserializer: JsonDeserializer<GithubUser> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): GithubUser {
        val username = json.asJsonObject.get("login")?.asString ?: ""
        val avatar = json.asJsonObject.get("avatar_url")?.asString ?: ""
        return GithubUser(username, emptyList(), avatar)
    }
}