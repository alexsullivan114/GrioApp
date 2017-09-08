package com.alexsullivan.griointerviewapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexsullivan.griointerviewapp.github.GithubNetworkRepository
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity(), StartView {
    // Build our presenter. This is left mutable so it can be altered in
    // espresso tests, and a dummy presenter can be inserted.
    var presenter: StartPresenter = StartPresenterImpl(GithubNetworkRepository.build())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        startButton.setOnClickListener {
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

    override fun showWinnerScreen(winner: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNetworkError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserOneInputError() {
        textLayoutOne.error = getString(R.string.invalid_username)
    }

    override fun showUserTwoInputError() {
        textLayoutTwo.error = getString(R.string.invalid_username)
    }
}
