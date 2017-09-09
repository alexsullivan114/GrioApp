package com.alexsullivan.griointerviewapp.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Scene
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import com.alexsullivan.griointerviewapp.R
import com.alexsullivan.griointerviewapp.extensions.addEndListener
import com.alexsullivan.griointerviewapp.github.GithubUser
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details_winner.*

class DetailsActivity: AppCompatActivity(), DetailsView {

    // Build our presenter. This class is simple enough that its presenter doesn't do
    // much of anything, but I'm leaving it in to ensure the class stays maintainable as it grows.
    lateinit var presenter: DetailsPresenter

    companion object {
        val WINNER_KEY = "WinnerKey"
        val LOSER_KEY = "LoserKey"
        fun buildIntent(context: Context, winner: GithubUser, loser: GithubUser): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(WINNER_KEY, winner).putExtra(LOSER_KEY, loser)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = DetailsPresenterImpl(intent.getParcelableExtra<GithubUser>(WINNER_KEY),
            intent.getParcelableExtra<GithubUser>(LOSER_KEY))
        setContentView(R.layout.activity_details_winner)
        val winningUser = intent.getParcelableExtra<GithubUser>(WINNER_KEY)
        centerText.text = getString(R.string.winner_is, winningUser.name)
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
        centerText.postDelayed({animateWinnerText()}, 1500)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach(this)
    }

    private fun showUsersRepoList(winner: GithubUser, loser: GithubUser) {
        centerText.visibility = View.GONE
        pager.visibility = View.VISIBLE
        pager.adapter = DetailsPagerAdapter(supportFragmentManager, winner, loser)
    }

    // Animate our winner text out and change scenes to our pager view.
    private fun animateWinnerText() {
        val transition = Slide(Gravity.TOP)
        transition.addEndListener {
            val users = presenter.provideUsers()
            showUsersRepoList(users.first, users.second)
        }
        val scene = Scene.getSceneForLayout(root, R.layout.activity_details, this)
        TransitionManager.go(scene, transition)
    }
}