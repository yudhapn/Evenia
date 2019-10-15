package com.gemastik.evenia.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Vacancy(
    @SerializedName("vacancyId")
    var vacancyId: String = "",
    @SerializedName("isVerified")
    var isVerified: Boolean = false,
    @SerializedName("image")
    var image: Int = 0,
    @SerializedName("inStage")
    var inStage: Int = 0,
    @SerializedName("backgroundImage")
    var backgroundImage: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("category")
    var category: String = "",
    @SerializedName("location")
    var location: String = "",
    @SerializedName("picName")
    var picName: String = "",
    @SerializedName("picPhone")
    var picPhone: String = "",
    @SerializedName("picIdentityType")
    var picIdentityType: String = "",
    @SerializedName("picIdentityNumber")
    var picIdentityNumber: String = "",
    @SerializedName("identityImage")
    var identityImage: String = "",
    @SerializedName("identityPersonImage")
    var identityPersonImage: String = "",
    @SerializedName("createdOn")
    @ServerTimestamp
    val createdOn: Date = Calendar.getInstance().time
) : Parcelable