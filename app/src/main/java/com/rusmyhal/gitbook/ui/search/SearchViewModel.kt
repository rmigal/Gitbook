package com.rusmyhal.gitbook.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rusmyhal.gitbook.model.DispatchersProvider
import com.rusmyhal.gitbook.model.data.server.Error
import com.rusmyhal.gitbook.model.data.server.Success
import com.rusmyhal.gitbook.model.data.server.entity.SearchResponse
import com.rusmyhal.gitbook.model.data.server.entity.SearchUser
import com.rusmyhal.gitbook.model.repository.SearchRepository
import com.rusmyhal.gitbook.utils.arch.SingleLiveEvent
import com.rusmyhal.gitbook.utils.extensions.awaitResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = dispatchersProvider.main + job

    private val _users = MutableLiveData<List<SearchUser>>()
    val users: LiveData<List<SearchUser>>
        get() = _users

    private val _error = SingleLiveEvent<String>()
    val error: LiveData<String>
        get() = _error

    private val _navigateToProfile = SingleLiveEvent<String>()
    val navigateToProfile: LiveData<String>
        get() = _navigateToProfile

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
        _navigateToProfile.value = username
    }

    companion object {

        const val TAG = "SearchViewModel"
    }

}