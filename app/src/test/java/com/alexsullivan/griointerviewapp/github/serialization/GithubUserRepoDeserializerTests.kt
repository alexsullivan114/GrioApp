package com.alexsullivan.griointerviewapp.github.serialization

import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import com.google.gson.JsonParser
import org.junit.Assert
import org.junit.Test

class GithubUserRepoDeserializerTests {

    @Test
    fun testExpectedDeserialization() {
        val deserializer = GithubUserRepoDeserializer()
        val jsonArray = JsonParser().parse(alexRepoCannedResponse).asJsonArray

        val dbRepo = GithubUserRepo("Database-Upgrade-Annotation-Processor", 0)
        Assert.assertEquals(dbRepo, deserializer.deserialize(jsonArray[0], null, null))

        val gifRecipesRepo = GithubUserRepo("GifRecipes", 1)
        Assert.assertEquals(gifRecipesRepo, deserializer.deserialize(jsonArray[1], null, null))

        val grioApp = GithubUserRepo("GrioApp", 0)
        Assert.assertEquals(grioApp , deserializer.deserialize(jsonArray[2], null, null))

        val invisionInterviewAppRepo = GithubUserRepo("InvisionInterviewApp", 0)
        Assert.assertEquals(invisionInterviewAppRepo , deserializer.deserialize(jsonArray[3], null, null))

        val materialFitnessRepo = GithubUserRepo("MaterialFitness", 0)
        Assert.assertEquals(materialFitnessRepo , deserializer.deserialize(jsonArray[4], null, null))

        val nibletsRepo = GithubUserRepo("niblets", 0)
        Assert.assertEquals(nibletsRepo , deserializer.deserialize(jsonArray[5], null, null))

        val trends = GithubUserRepo("Trends", 0)
        Assert.assertEquals(trends , deserializer.deserialize(jsonArray[6], null, null))
    }

    @Test(expected = IllegalStateException::class)
    fun testMalformedJson() {
        val json = JsonParser().parse(alexMalformedResponse)
        val repos = GithubUserRepoDeserializer().deserialize(json, null, null)
        Assert.fail("Expected a parsing exception on json response!")
    }
}