package com.rusmyhal.gitbook.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rusmyhal.gitbook.model.DispatchersProvider
import com.rusmyhal.gitbook.model.data.server.Error
import com.rusmyhal.gitbook.model.data.server.Success
import com.rusmyhal.gitbook.model.data.server.entity.SearchResponse
import com.rusmyhal.gitbook.model.entity.User
import com.rusmyhal.gitbook.model.repository.SearchRepository
import com.rusmyhal.gitbook.utils.arch.SingleLiveEvent
import com.rusmyhal.gitbook.utils.awaitResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = dispatchersProvider.main + job

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    private val _error = SingleLiveEvent<String>()
    val error: LiveData<String>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun searchUsers(query: String) = launch {
        _loading.value = true
        val response = withContext(dispatchersProvider.io) {
            searchRepository.searchUsers(query).awaitResult()
        }

        when (response) {
            is Success<SearchResponse> -> _users.value = response.data.users
            is Error -> _error.value = response.errorMessage
        }
        _loading.value = false
    }

    fun onUserClick(username: String) {
        Log.d(TAG, "onUserClick() $username")
    }

    companion object {

        const val TAG = "SearchViewModel"
    }

}