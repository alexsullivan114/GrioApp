package com.alexsullivan.griointerviewapp

import com.alexsullivan.griointerviewapp.github.GithubRepository
import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Test

class StartPresenterTests {

    @Test
    fun testNormalRepoComparisons() {
        val repository = object: GithubRepository {
            override fun loadRepositoryData(username: String): Observable<GithubUserRepo> {
                if (username == "Alex") {
                    val searchRepo = GithubUserRepo("Search", 5)
                    val messengerRepo = GithubUserRepo("Messenger", 3)
                    val graphRepo = GithubUserRepo("Graph", 9)

                    return Observable.fromArray(searchRepo, messengerRepo, graphRepo)
                } else {
                    val droidRepo = GithubUserRepo("droid", 0)
                    val iphoneRepo = GithubUserRepo("iphone", 1)
                    val windowsRepo = GithubUserRepo("windows", 1)

                    return Observable.fromArray(droidRepo, iphoneRepo, windowsRepo)
                }
            }
        }

        val view = object: StartView {
            var winnerSelected = false
            override fun showWinnerScreen(winner: String) {
                Assert.assertEquals("Alex", winner)
                winnerSelected = true
            }

            override fun showNetworkError() {}
            override fun showUserOneInputError() {}
            override fun showUserTwoInputError() {}
        }

        val testScheduler = TestScheduler()
        val presenter = StartPresenterImpl(repository, testScheduler, testScheduler)
        presenter.attach(view)
        presenter.startClicked("Alex", "Nick")
        testScheduler.triggerActions()
        Assert.assertTrue(view.winnerSelected)
    }

    @Test
    fun testInvalidUsername() {
        val repository = object: GithubRepository {
            override fun loadRepositoryData(username: String): Observable<GithubUserRepo> {
                if (username == "Alex") {
                    val searchRepo = GithubUserRepo("Search", 5)
                    val messengerRepo = GithubUserRepo("Messenger", 3)
                    val graphRepo = GithubUserRepo("Graph", 9)

                    return Observable.fromArray(searchRepo, messengerRepo, graphRepo)
                } else {
                    val droidRepo = GithubUserRepo("droid", 0)
                    val iphoneRepo = GithubUserRepo("iphone", 1)
                    val windowsRepo = GithubUserRepo("windows", 1)

                    return Observable.fromArray(droidRepo, iphoneRepo, windowsRepo)
                }
            }
        }

        val view = object: StartView {
            var winnerSelected = false
            var userTwoInputError = false
            var userOneInputError = false
            override fun showWinnerScreen(winner: String) {
                Assert.assertEquals("Alex", winner)
                winnerSelected = true
            }

            override fun showNetworkError() {}
            override fun showUserOneInputError() {
                userOneInputError = true
            }
            override fun showUserTwoInputError() {
                userTwoInputError = true
            }
        }

        val testScheduler = TestScheduler()
        val presenter = StartPresenterImpl(repository, testScheduler, testScheduler)
        presenter.attach(view)
        presenter.startClicked("Alex", "")
        testScheduler.triggerActions()
        Assert.assertFalse(view.winnerSelected)
        Assert.assertFalse(view.userOneInputError)
        Assert.assertTrue(view.userTwoInputError)
    }
}