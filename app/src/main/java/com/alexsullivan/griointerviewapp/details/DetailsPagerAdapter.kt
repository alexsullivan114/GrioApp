package com.alexsullivan.griointerviewapp.details

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.alexsullivan.griointerviewapp.details.detailslist.DetailsListFragment
import com.alexsullivan.griointerviewapp.github.GithubUser

class DetailsPagerAdapter(fragmentManager: FragmentManager,
                          private val firstUser: GithubUser,
                          private val secondUser: GithubUser): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = DetailsListFragment.newInstance(getUser(position))
    override fun getCount() = 2
    override fun getPageTitle(position: Int) = getUser(position).name

    private fun getUser(position: Int) = if(position == 0) firstUser else secondUser
}