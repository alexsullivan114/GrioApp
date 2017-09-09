package com.alexsullivan.griointerviewapp

import com.alexsullivan.griointerviewapp.github.GithubRepository
import com.alexsullivan.griointerviewapp.github.GithubUser
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
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
        val url = "https://github.com/$name/"
        view?.openUserWebview(url)
    }

    private fun chooseWinner(userOne: GithubUser, userTwo: GithubUser) {
        if (userOne.numStars() > userTwo.numStars()) {
            view?.showWinnerScreen(userOne, userTwo)
        } else {
            view?.showWinnerScreen(userTwo, userOne)
        }
    }

    private fun bindInputs() {
        // Set out first user to null when the user types something so we've got up to date
        // data!
        view?.let { view ->
            val userOneTextObservable = view.getUserOneTextInputObservable()
                .doOnNext { firstUser = null }
            val userTwoTextObservable = view.getUserTwoTextInputObservable()
                .doOnNext { secondUser = null }
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

    private fun bindUserInputObservable(observable: Observable<String>,
                                        successBlock: (GithubUser) -> Unit,
                                        emptyBlock: () -> Unit) {
        disposables.add(observable
            .debounce(500, TimeUnit.MILLISECONDS, backgroundScheduler)
            .flatMapSingle { repository.loadUserData(it).toList() }
            .subscribeOn(backgroundScheduler)
            .observeOn(foregroundScheduler)
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