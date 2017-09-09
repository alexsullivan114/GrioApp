package com.alexsullivan.griointerviewapp.github

import android.os.Parcel
import android.os.Parcelable

data class GithubUser(val name: String, val repos: List<GithubUserRepo>, val avatarUrl: String) : Parcelable {
    fun numStars(): Int {
        if (repos.isEmpty()) {
            return 0
        } else {
            return repos.map { it.stars }.reduceRight { i, acc -> i + acc }
        }
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.createTypedArrayList(GithubUserRepo.CREATOR),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeTypedList(repos)
        writeString(avatarUrl)
    }

    companion object CREATOR : Parcelable.Creator<GithubUser> {
        override fun createFromParcel(parcel: Parcel) = GithubUser(parcel)

        override fun newArray(size: Int): Array<GithubUser?> = arrayOfNulls(size)
    }
}