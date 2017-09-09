package com.alexsullivan.griointerviewapp.details.detailslist

import com.alexsullivan.griointerviewapp.extensions.contentEquals
import com.alexsullivan.griointerviewapp.github.GithubUser
import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import org.junit.Assert
import org.junit.Test

class DetailsListPresenterTests {

    @Test
    fun testProperSorting() {
        val repo1 = GithubUserRepo("Testing", 5)
        val repo2 = GithubUserRepo("A lil more testing", 2)
        val repo3 = GithubUserRepo("Tests for dayz", 12)
        val repo4 = GithubUserRepo("Jim", 5)
        val user = GithubUser("Alex", listOf(repo1, repo2, repo3, repo4), "fakeUrl")
        val presenter = DetailsListPresenterImpl(user)
        Assert.assertTrue(presenter.getUserRepos() contentEquals listOf(repo3, repo1, repo4, repo2))
    }

    @Test
    fun testEmptyListSorting() {
        val user = GithubUser("Alex", emptyList(), "fakeUrl")
        val presenter = DetailsListPresenterImpl(user)
        Assert.assertTrue(presenter.getUserRepos() contentEquals listOf())
    }

    @Test
    fun testProperUrlReceived() {
        val expectedUrl = "https://github.com/alexsullivan114/GifRecipes"
        val view = object: DetailsListView {
            var openWebviewCalled = false
            override fun openRepoWebview(url: String) {
                Assert.assertEquals(expectedUrl, url)
                openWebviewCalled = true
            }
        }
        val repo = GithubUserRepo("GifRecipes", 1)
        val user = GithubUser("alexsullivan114", listOf(repo), "fakeUrl")
        val presenter = DetailsListPresenterImpl(user)
        presenter.attach(view)
        presenter.repoClicked(repo)
        Assert.assertTrue(view.openWebviewCalled)
    }

    @Test
    fun testBailOnImproperUrl() {
        val view = object: DetailsListView {
            override fun openRepoWebview(url: String) {
                Assert.fail("Shouldn't have called into open repo webview!")
            }
        }
        // Check for empty repo name.
        val invalidRepo = GithubUserRepo("", 1)
        val user = GithubUser("alexsullivan114", listOf(invalidRepo), "fakeUrl")
        val firstPresenter = DetailsListPresenterImpl(user)
        firstPresenter.attach(view)
        firstPresenter.repoClicked(invalidRepo)
        // And check for empty username.
        val validRepo = GithubUserRepo("Test", 5)
        val invalidUser = GithubUser("", listOf(validRepo), "fakeUrl")
        val secondPresenter = DetailsListPresenterImpl(invalidUser)
        secondPresenter.attach(view)
        secondPresenter.repoClicked(validRepo)
    }
}