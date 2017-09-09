package com.alexsullivan.griointerviewapp

import com.alexsullivan.griointerviewapp.github.GithubRepository
import com.alexsullivan.griointerviewapp.github.GithubUser
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

class StartPresenterImpl(private val repository: GithubRepository,
                         private val backgroundScheduler: Scheduler,
                         private val foregroundScheduler: Scheduler): StartPresenter() {

    private val githubDisposable: Disposable? = null

    override fun detach(view: StartView) {
        super.detach(view)
        githubDisposable?.dispose()
    }

    override fun startClicked(usernameOne: String, usernameTwo: String) {
        // Guard against invalid usernames - thinking of mainly the empty string here.
        if (!validateUsername(usernameOne)) {
            view?.showUserOneInputError()
            return
        } else if (!validateUsername(usernameTwo)) {
            view?.showUserTwoInputError()
            return
        }
        // Now that we know we have valid usernames (at least, the string is valid) let's fetch our
        // list of repos and compare star numbers!
        val firstUser = repository.loadRepositoryData(usernameOne).toList().map { GithubUser(usernameOne, it) }
        val secondUser = repository.loadRepositoryData(usernameTwo).toList().map { GithubUser(usernameTwo, it) }
        Single.zip(firstUser, secondUser, BiFunction<GithubUser, GithubUser, Pair<GithubUser, GithubUser>> { t1, t2 ->  t1 to t2})
            .subscribeOn(backgroundScheduler)
            .observeOn(foregroundScheduler)
            .subscribe({
                chooseWinner(it.first, it.second)
            }, {
                view?.showNetworkError()
            })
    }

    private fun chooseWinner(userOne: GithubUser, userTwo: GithubUser) {
        if (userOne.numStars() > userTwo.numStars()) {
            view?.showWinnerScreen(userOne, userTwo)
        } else {
            view?.showWinnerScreen(userTwo, userOne)
        }
    }

    private fun validateUsername(username: String?) = !username.isNullOrEmpty()

    /**
     * Convenience function to convert a list of user repos into a total number of stars for each
     * user repo.
     * @param username: The username to use when fetching the repo information
     *
     * TODO: Make a special exception if the repo isn't found, and then forward that along here.
     */
    private fun buildStarCountObservable(username: String): Single<Int> {
        return repository.loadRepositoryData(username)
            .map { it.stars }
            .reduce(0, {acc, value ->
                acc + value
            })
    }
}