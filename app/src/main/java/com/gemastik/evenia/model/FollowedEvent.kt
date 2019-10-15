package com.gemastik.evenia.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FollowedEvent(
    @SerializedName("followedEventId")
    var followedEventId: String = "",
@SerializedName("image")
    var image: Int = 0,
    @SerializedName("name")
    var name: String = ""
) : Parcelable