package com.alexsullivan.griointerviewapp.details.detailslist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexsullivan.griointerviewapp.R
import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import kotlinx.android.synthetic.main.fragment_details_list.view.*

class DetailsListFragment: Fragment(), DetailsListView {

    companion object {
        val REPOS_KEY = "reposKey"
        fun newInstance(repos: List<GithubUserRepo>): DetailsListFragment {
            val bundle = Bundle()
            bundle.putParcelableArray(REPOS_KEY, repos.toTypedArray())
            val fragment = DetailsListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details_list, container, false)
        val repos = arguments.getParcelableArray(REPOS_KEY) as Array<GithubUserRepo>
        repos.sortByDescending { it.stars }
        view.list.layoutManager = LinearLayoutManager(context)
        view.list.adapter = UserRepoListAdapter(repos)
        return view
    }
}