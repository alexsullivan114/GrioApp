package com.alexsullivan.griointerviewapp.details.detailslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexsullivan.griointerviewapp.R
import com.alexsullivan.griointerviewapp.details.detailslist.UserRepoListAdapter.RepoViewHolder
import com.alexsullivan.griointerviewapp.github.GithubUserRepo
import kotlinx.android.synthetic.main.adapter_repo_item.view.*

class UserRepoListAdapter(private val repoList: Array<GithubUserRepo>): RecyclerView.Adapter<RepoViewHolder>() {
    override fun getItemCount() = repoList.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repoList[position]
        holder.view.name.text = repo.title
        holder.view.numStars.text = holder.view.context.getString(R.string.star_count, repo.stars)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_repo_item, parent, false)
        return RepoViewHolder(view)
    }

    inner class RepoViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    }
}