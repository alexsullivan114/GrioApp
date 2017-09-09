package com.alexsullivan.griointerviewapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.alexsullivan.griointerviewapp.details.DetailsActivity
import com.alexsullivan.griointerviewapp.github.GithubNetworkRepository
import com.alexsullivan.griointerviewapp.github.GithubUser
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
            val inputOne = userInputOne.text.toString()
            val inputTwo = userInputTwo.text.toString()
            presenter.startClicked(inputOne, inputTwo)
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
}
