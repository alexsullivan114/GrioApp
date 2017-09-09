package com.alexsullivan.griointerviewapp.github

import android.os.Parcel
import android.os.Parcelable

data class GithubUserRepo(val title: String, val stars: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(stars)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<GithubUserRepo> {
        override fun createFromParcel(parcel: Parcel) = GithubUserRepo(parcel)
        override fun newArray(size: Int): Array<GithubUserRepo?> = arrayOfNulls(size)
    }
}