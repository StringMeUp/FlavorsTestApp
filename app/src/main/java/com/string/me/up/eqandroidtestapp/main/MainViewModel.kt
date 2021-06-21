package com.string.me.up.eqandroidtestapp.main

import androidx.lifecycle.*
import com.string.me.up.eqandroidtestapp.model.Item
import com.string.me.up.eqandroidtestapp.repository.MainRepository
import com.string.me.up.eqandroidtestapp.repository.State
import com.string.me.up.eqandroidtestapp.util.SingleLiveEvent
import kotlinx.coroutines.launch


class MainViewModel : ViewModel(), LifecycleObserver {

    val appData = MutableLiveData<Item>()
    val errorResponse = SingleLiveEvent<String>()
    val isLoading = MutableLiveData<Boolean>()
    val endOfPaginationReached = MutableLiveData<Boolean>()
    private val nextPage = MutableLiveData<Int>()

    init {
        nextPage.value = 1
    }

    fun getItems() = viewModelScope.launch {
        isLoading.postValue(true)
        when (val response = MainRepository.fetchRemoteItems(nextPage.value!!)) {
            is State.Success -> {
                isLoading.postValue(false)
                appData.postValue(response.value)
                if (response.value.page < 3) {
                    nextPage.postValue(incrementPage(response.value.page))
                }
                if (response.value.page == 2) {
                    endOfPaginationReached.postValue(true)
                }
            }
            is State.Failure -> {
                isLoading.postValue(false)
                errorResponse.postValue(response.error)
            }
            is State.Exception -> {
                isLoading.postValue(false)
                errorResponse.postValue("Unexpected err: ${response.throwable}")
            }
        }
    }

    private fun incrementPage(nextPage: Int): Int =
        nextPage.inc()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onLifeCyclePause() {
        nextPage.value = 1
        endOfPaginationReached.value = false
    }
}