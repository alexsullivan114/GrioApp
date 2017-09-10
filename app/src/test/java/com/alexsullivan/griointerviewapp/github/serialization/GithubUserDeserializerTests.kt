package com.alexsullivan.griointerviewapp.github.serialization

import com.google.gson.JsonParser
import org.junit.Assert
import org.junit.Test

class GithubUserDeserializerTests {
    @Test
    fun testNormalDeserialization() {
        val element = JsonParser().parse(alexUserCannedResponse)
        val deserializer = GithubUserDeserializer()
        val user = deserializer.deserialize(element, null, null)
        Assert.assertEquals("alex", user.name)
        Assert.assertEquals("https://avatars2.githubusercontent.com/u/772?v=4", user.avatarUrl)
    }

    @Test
    fun testEmptyFields() {
        val element = JsonParser().parse(alexUserEmptyFieldsCannedResponse)
        val deserializer = GithubUserDeserializer()
        val user = deserializer.deserialize(element, null, null)
        Assert.assertEquals("", user.name)
        Assert.assertEquals("", user.avatarUrl)
    }
}