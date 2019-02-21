package com.rusmyhal.gitbook.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import com.rusmyhal.gitbook.model.DispatchersProvider
import com.rusmyhal.gitbook.model.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = dispatchersProvider.main + job

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun searchUsers(query: String) = launch {
        val response = searchRepository.searchUsers(query).await()
        Log.d(TAG, "searchUsers() ${response.users}")
    }

    companion object {
        const val TAG = "SearchViewModel"
    }

}