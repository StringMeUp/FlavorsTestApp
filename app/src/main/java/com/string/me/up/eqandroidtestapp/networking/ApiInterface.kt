package com.string.me.up.eqandroidtestapp.networking

import com.string.me.up.eqandroidtestapp.model.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("data")
    suspend fun getRemoteItems(@Query("page") page: Int): Response<Item>
}