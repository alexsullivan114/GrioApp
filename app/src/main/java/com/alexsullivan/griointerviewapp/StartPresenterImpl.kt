package com.alexsullivan.griointerviewapp

import com.alexsullivan.griointerviewapp.github.GithubRepository
import com.alexsullivan.griointerviewapp.github.GithubUser
import com.alexsullivan.griointerviewapp.github.buildGithubUserUrl
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import java.io.InterruptedIOException
import java.util.concurrent.TimeUnit

class StartPresenterImpl(private val repository: GithubRepository,
                         private val backgroundScheduler: Scheduler,
                         private val foregroundScheduler: Scheduler): StartPresenter() {

    private val disposables = CompositeDisposable()

    private var firstUser: GithubUser? = null
    private var secondUser: GithubUser? = null

    override fun attach(view: StartView) {
        super.attach(view)
        bindInputs()
    }

    override fun detach(view: StartView) {
        super.detach(view)
        disposables.clear()
    }

    /**
     * The user clicked start. If either of our github users are missing,
     * we'll show an error message.
     */
    override fun startClicked() {
        if (firstUser == null) {
            view?.showUserOneInputError()
        } else if (secondUser == null) {
            view?.showUserTwoInputError()
        } else {
            firstUser?.let { firstUser -> secondUser?.let { secondUser ->
                chooseWinner(firstUser, secondUser)
            } }
        }
    }

    override fun firstUserAvatarClicked() {
        firstUser?.let {
            userAvatarClicked(it.name)
        }
    }

    override fun secondUserAvatarClicked() {
        secondUser?.let {
            userAvatarClicked(it.name)
        }
    }

    private fun userAvatarClicked(name: String) {
        view?.openUserWebview(buildGithubUserUrl(name))
    }

    // Simple helper function to compare two github users and choose a winner,
    // notifying the UI to react accordingly.
    private fun chooseWinner(userOne: GithubUser, userTwo: GithubUser) {
        if (userOne.numStars() > userTwo.numStars()) {
            view?.showWinnerScreen(userOne, userTwo)
        } else {
            view?.showWinnerScreen(userTwo, userOne)
        }
    }

    /**
     * Helper function to coordinate our text input observables reaction logic.
     */
    private fun bindInputs() {
        // Set out first user to null when the user types something so since they're looking up
        // a new user.
        view?.let { view ->
            val userOneTextObservable = view.getUserOneTextInputObservable().doOnNext { firstUser = null }
            val userTwoTextObservable = view.getUserTwoTextInputObservable().doOnNext { secondUser = null }
            // Now bind our inputs defined above and show some feedback depending on the
            // result of the call.
            bindUserInputObservable(userOneTextObservable, {
                firstUser = it
                view.updateUserOneAvatar(it.avatarUrl)
                view.hideUserOneInputError()
            }, {
                firstUser = null
                view.showUserOneInputError()
            })

            bindUserInputObservable(userTwoTextObservable, {
                secondUser = it
                view.updateUserTwoAvatar(it.avatarUrl)
                view.hideUserTwoInputError()
            }, {
                secondUser = null
                view.showUserTwoInputError()
            })
        }
    }

    /**
     * Helper function to bind our text streams and look up users.
     * @param textObservable Observable of strings
     * @param successBlock Block to call after a user is retrieved
     * @param emptyBlock block to call if no users are returned.
     */
    private fun bindUserInputObservable(textObservable: Observable<String>,
                                        successBlock: (GithubUser) -> Unit,
                                        emptyBlock: () -> Unit) {
        disposables.add(textObservable
            // Debounce this input - we'll only act on this string input if the user
            // stops typing for 500 milliseconds. That way we only make an API call
            // when the user is done (hopefully) typing.
            .debounce(500, TimeUnit.MILLISECONDS, backgroundScheduler)
            .flatMapSingle { repository.loadUserData(it).toList() }
            .subscribeOn(backgroundScheduler)
            .observeOn(foregroundScheduler)
            // Our thread can be interrupted - but we don't want to kill the stream
            // if that happens. As such, we'll just retry if we can that exception.
            // Otherwise, show it to the user.
            .retry { t1, t2 -> t2 is InterruptedIOException }
            // If we get zero users back utilize the empty block.
            // If we get > 0 users, take the first one and pass it on through to the
            // success block.
            .subscribe({users ->
                if (users.size == 0) {
                    emptyBlock()
                } else {
                    successBlock(users[0])
                }
            }, {
                view?.showNetworkError()
            }))
    }
}