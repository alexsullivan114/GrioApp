package com.alexsullivan.griointerviewapp.details.detailslist

import com.alexsullivan.griointerviewapp.baseview.ViewInterface

interface DetailsListView: ViewInterface {
    fun openRepoWebview(url: String)
}