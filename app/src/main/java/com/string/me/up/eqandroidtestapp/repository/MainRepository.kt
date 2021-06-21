package com.string.me.up.eqandroidtestapp.repository

import com.string.me.up.eqandroidtestapp.model.Item
import com.string.me.up.eqandroidtestapp.networking.RetrofitBuilder
import com.string.me.up.eqandroidtestapp.util.getError

object MainRepository {
    private val builder = RetrofitBuilder.create()
    suspend fun fetchRemoteItems(page: Int): State<Item> = try {
        val response = builder.getRemoteItems(page)
        if (response.isSuccessful) State.Success(response.body()!!)
        else State.Failure(response.getError()?.error!!)
    } catch (t: Throwable) {
        State.Exception(t)
    }
}

sealed class State<out T> {
    data class Success<out T>(val value: T) : State<T>()
    data class Failure<out T>(val error: String, val errorInfo: String? = null) : State<T>()
    data class Exception<out T>(val throwable: Throwable) : State<T>()
}