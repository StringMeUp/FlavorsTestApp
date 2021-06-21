package com.string.me.up.eqandroidtestapp.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.string.me.up.eqandroidtestapp.model.ErrorInfo
import retrofit2.Response
import java.io.File

fun <T> Response<T>.getError(): ErrorInfo? {
    return Gson().fromJson(this.errorBody()!!.charStream(), ErrorInfo::class.java)
}

@BindingAdapter("glide")
fun fetchImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view).load(url).into(view)
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Context.downloadImage(url: String) {
    val title = url.substring(url.lastIndexOf("/") + 1)
    val directory = File(Environment.DIRECTORY_DOWNLOADS, title)
    if (!directory.exists()) directory.mkdir()
    val downloadManager =
        this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request = DownloadManager.Request(Uri.parse(url))
    request.setAllowedNetworkTypes(
        DownloadManager.Request.NETWORK_WIFI or
                DownloadManager.Request.NETWORK_MOBILE
    )
    request.setAllowedOverRoaming(false)
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)
    downloadManager.enqueue(request)
}
