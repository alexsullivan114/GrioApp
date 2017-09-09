package com.alexsullivan.griointerviewapp.details.detailslist

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexsullivan.griointerviewapp.R
import com.alexsullivan.griointerviewapp.github.GithubUser
import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import kotlinx.android.synthetic.main.fragment_details_list.view.*

class DetailsListFragment: Fragment(), DetailsListView, UserRepoListAdapter.ClickListener {

    lateinit var presenter: DetailsListPresenter

    companion object {
        val USER_KEY = "reposKey"
        fun newInstance(user: GithubUser): DetailsListFragment {
            val bundle = Bundle()
            bundle.putParcelable(USER_KEY, user)
            val fragment = DetailsListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = DetailsListPresenterImpl(arguments.getParcelable<GithubUser>(USER_KEY))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details_list, container, false)
        view.list.layoutManager = LinearLayoutManager(context)
        view.list.adapter = UserRepoListAdapter(presenter.getUserRepos(), this)
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.attach(this)
    }

    override fun repoClicked(repo: GithubUserRepo) {
        presenter.repoClicked(repo)
    }

    override fun openRepoWebview(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
        builder.build().launchUrl(activity, Uri.parse(url))
    }
}