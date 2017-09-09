package com.alexsullivan.griointerviewapp

import com.alexsullivan.griointerviewapp.github.GithubRepository
import com.alexsullivan.griointerviewapp.github.GithubUser
import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class StartPresenterTests {
    var firstUserInputObservable = PublishSubject.create<String>()
    var secondUserInputObservable = PublishSubject.create<String>()

    @Before
    fun setup() {
        firstUserInputObservable = PublishSubject.create<String>()
        secondUserInputObservable = PublishSubject.create<String>()
    }

    @Test
    fun testSuccesfulCheck() {
        val fakeUrl1 = "fakeUrl1"
        val fakeUrl2 = "faleUrl2"
        val alexRepo = GithubUserRepo("test1", 5)
        val jakeRepo = GithubUserRepo("test2", 140)
        val alexUser = GithubUser("Alex", listOf(alexRepo), fakeUrl1)
        val jakeUser = GithubUser("Jake", listOf(jakeRepo), fakeUrl2)
        var userOneAvatarUpdated = false
        var userTwoAvatarUpdated = false
        var showWinnerScreenCalled = false

        val view = object: StartViewAdapter() {
            override fun getUserOneTextInputObservable() = Observable.just("Alex")
            override fun getUserTwoTextInputObservable() = Observable.just("Jake")
            override fun updateUserOneAvatar(url: String) {
                Assert.assertEquals(fakeUrl1, url)
                userOneAvatarUpdated = true
            }

            override fun updateUserTwoAvatar(url: String) {
                Assert.assertEquals(fakeUrl2, url)
                userTwoAvatarUpdated = true
            }

            override fun showWinnerScreen(winner: GithubUser, loser: GithubUser) {
                Assert.assertEquals(jakeUser, winner)
                showWinnerScreenCalled = true
            }
        }

        val repository = object: GithubRepository {
            override fun loadUserData(username: String): Observable<GithubUser> {
                return if (username == alexUser.name) {
                    Observable.just(alexUser)
                } else {
                    Observable.just(jakeUser)
                }
            }
        }

        val backgroundScheduler = TestScheduler()
        val foregroundScheduler = TestScheduler()

        val presenter = StartPresenterImpl(repository, backgroundScheduler, foregroundScheduler)
        presenter.attach(view)
        backgroundScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        backgroundScheduler.triggerActions()
        foregroundScheduler.triggerActions()
        Assert.assertTrue(userOneAvatarUpdated)
        Assert.assertTrue(userTwoAvatarUpdated)
        presenter.startClicked()
        Assert.assertTrue(showWinnerScreenCalled)
    }

    @Test
    fun testInvalidUser() {
        var sawInputOneError = false
        val view = object: StartViewAdapter() {
            override fun showUserOneInputError() {
                sawInputOneError = true
            }
        }

        val repository = object: GithubRepository {
            override fun loadUserData(username: String): Observable<GithubUser> = Observable.empty()
        }

        val scheduler = TestScheduler()
        val presenter = StartPresenterImpl(repository, scheduler, scheduler)
        presenter.attach(view)
        presenter.startClicked()
        Assert.assertTrue(sawInputOneError)
    }
}