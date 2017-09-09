package com.alexsullivan.griointerviewapp.github

import android.os.Parcel
import android.os.Parcelable

class GithubUser(val name: String, val repos: List<GithubUserRepo>) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(GithubUserRepo)) {
    }

    fun numStars(): Int {
        if (repos.isEmpty()) {
            return 0
        } else {
            return repos.map { it.stars }.reduceRight { i, acc -> i + acc }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeTypedList(repos)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<GithubUser> {
        override fun createFromParcel(parcel: Parcel) = GithubUser(parcel)

        override fun newArray(size: Int): Array<GithubUser?> = arrayOfNulls(size)
    }
}