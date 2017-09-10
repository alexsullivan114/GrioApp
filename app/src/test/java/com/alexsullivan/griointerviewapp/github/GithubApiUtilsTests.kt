package com.alexsullivan.griointerviewapp.github

import org.junit.Assert
import org.junit.Test

class GithubApiUtilsTests {
    @Test
    fun testBuildUserUrl() {
        val name = "alex"
        Assert.assertEquals("https://github.com/alex/", buildGithubUserUrl(name))
    }

    @Test
    fun testBuildRepoUrl() {
        val name = "alex"
        val repo = "gifrecipes"
        Assert.assertEquals("https://github.com/alex/gifrecipes", buildGithubRepoUrl(name, repo))
    }
}