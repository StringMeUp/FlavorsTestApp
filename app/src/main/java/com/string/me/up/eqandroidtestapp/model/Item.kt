package com.string.me.up.eqandroidtestapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val page: Int,
    @SerializedName("max_pages")
    val maxPages: Int,
    val items: ArrayList<ItemDetails>
): Parcelable

@Parcelize
data class ItemDetails(
    val type: String,
    val name: String,
    val image: String,
    @SerializedName("ios_store")
    val iosStore: String,
    @SerializedName("ios_details")
    val iosDetails: IosDetails?,
    @SerializedName("android_store")
    val androidStore: String,
    @SerializedName("android_details")
    val androidDetails: AndroidDetails?
) : Parcelable

@Parcelize
data class IosDetails(val score: Double?, val size: Double?) : Parcelable

@Parcelize
data class AndroidDetails(val ratings: Int?, val score: Double?) : Parcelable

data class ErrorInfo(val error: String?, val moreInfo: String?)