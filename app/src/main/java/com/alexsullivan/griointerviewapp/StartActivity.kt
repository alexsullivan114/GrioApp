package com.alexsullivan.griointerviewapp

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.alexsullivan.griointerviewapp.details.DetailsActivity
import com.alexsullivan.griointerviewapp.extensions.textObservable
import com.alexsullivan.griointerviewapp.github.GithubNetworkRepository
import com.alexsullivan.griointerviewapp.github.GithubUser
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity(), StartView {
    // Build our presenter. This is left mutable so it can be altered in
    // espresso tests, and a dummy presenter can be inserted.
    var presenter: StartPresenter = StartPresenterImpl(GithubNetworkRepository.build(),
        Schedulers.io(), AndroidSchedulers.mainThread())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startButton.setOnClickListener {
            error.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            presenter.startClicked()
        }

        userOneAvatar.setOnClickListener {
            presenter.firstUserAvatarClicked()
        }

        userTwoAvatar.setOnClickListener {
            presenter.secondUserAvatarClicked()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach(this)
    }

    override fun updateUserOneAvatar(url: String) {
        Glide.with(this)
            .load(url)
            .into(userOneAvatar)
    }

    override fun updateUserTwoAvatar(url: String) {
        Glide.with(this)
            .load(url)
            .into(userTwoAvatar)
    }

    override fun getUserOneTextInputObservable() = userInputOne.textObservable()

    override fun getUserTwoTextInputObservable() = userInputTwo.textObservable()

    override fun showWinnerScreen(winner: GithubUser, loser: GithubUser) {
        progressBar.visibility = View.GONE
        startActivity(DetailsActivity.buildIntent(this, winner, loser))
    }

    override fun showNetworkError() {
        progressBar.visibility = View.GONE
        error.visibility = View.VISIBLE
    }

    override fun showUserOneInputError() {
        textLayoutOne.error = getString(R.string.invalid_username)
        progressBar.visibility = View.GONE
    }

    override fun showUserTwoInputError() {
        textLayoutTwo.error = getString(R.string.invalid_username)
        progressBar.visibility = View.GONE
    }

    override fun hideUserOneInputError() {
        textLayoutOne.isErrorEnabled = false
    }

    override fun hideUserTwoInputError() {
        textLayoutTwo.isErrorEnabled = false
    }

    override fun openUserWebview(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        builder.build().launchUrl(this, Uri.parse(url))
    }
}
