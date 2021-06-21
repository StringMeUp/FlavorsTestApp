package com.string.me.up.eqandroidtestapp.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.string.me.up.eqandroidtestapp.model.ItemDetails
import java.util.*


class DetailsViewModel : ViewModel() {

    val itemDetailsInfo = MutableLiveData<ItemDetails>()
    val androidRatings = MutableLiveData<Int>()
    val androidScores = MutableLiveData<String>()
    val iosRatings = MutableLiveData<Int>()
    val iosScores = MutableLiveData<String>()
    val type = MutableLiveData<String>()
    val isAndroid = MutableLiveData<Boolean>()
    val appSize = MutableLiveData<Double>()

    fun setItemDetails(itemDetails: ItemDetails) {
        itemDetailsInfo.value = itemDetails
        when {
            itemDetails.androidDetails != null -> {
                //in android and ios ratings map to score
                androidRatings.value = itemDetails.androidDetails.score?.toInt()
                androidScores.value = itemDetails.androidDetails.ratings.toString()
                isAndroid.value = true
            }
            itemDetails.iosDetails != null -> {
                iosRatings.value = itemDetails.iosDetails.score?.toInt()
                iosScores.value = itemDetails.iosDetails.score?.toString() ?: "0"
                appSize.value = itemDetails.iosDetails.size
                isAndroid.value = false
            }
        }

        type.value = itemDetails.type.capitalize(Locale.ROOT)
    }
}