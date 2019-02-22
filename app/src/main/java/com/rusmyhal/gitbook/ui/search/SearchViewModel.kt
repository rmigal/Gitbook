package com.rusmyhal.gitbook.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rusmyhal.gitbook.model.DispatchersProvider
import com.rusmyhal.gitbook.model.data.server.Error
import com.rusmyhal.gitbook.model.data.server.NetworkResponse
import com.rusmyhal.gitbook.model.data.server.Success
import com.rusmyhal.gitbook.model.data.server.entity.SearchResponse
import com.rusmyhal.gitbook.model.entity.User
import com.rusmyhal.gitbook.model.repository.SearchRepository
import com.rusmyhal.gitbook.utils.arch.SingleLiveEvent
import com.rusmyhal.gitbook.utils.awaitResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class SearchViewModel(
    private val searchRepository: SearchRepository,
    dispatchersProvider: DispatchersProvider
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = dispatchersProvider.main + job

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    private val _error = SingleLiveEvent<String>()
    val error: LiveData<String>
        get() = _error

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun searchUsers(query: String) = launch {
        val response: NetworkResponse<SearchResponse> =
            searchRepository.searchUsers(query).awaitResult()

        when (response) {
            is Success<SearchResponse> -> _users.value = response.data.users
            is Error -> _error.value = response.errorMessage
        }
    }

    fun onUserClick(username: String) {
        Log.d(TAG, "onUserClick() $username")
    }

    companion object {

        const val TAG = "SearchViewModel"
    }

}