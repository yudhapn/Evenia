package com.gemastik.evenia.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Participant(
    @SerializedName("participantId")
    var participantId: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("adress")
    var adress: String = "",
    @SerializedName("type")
    var type: String = "",
    @SerializedName("profileImage")
    var profileImage: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("token")
    var token: String = ""
) : Parcelable