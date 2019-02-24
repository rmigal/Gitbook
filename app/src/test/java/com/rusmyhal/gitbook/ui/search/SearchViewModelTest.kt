package com.rusmyhal.gitbook.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.rusmyhal.gitbook.TestDispatchers
import com.rusmyhal.gitbook.mockError
import com.rusmyhal.gitbook.model.data.server.entity.SearchUser
import com.rusmyhal.gitbook.model.repository.SearchRepository
import com.rusmyhal.gitbook.searchResponseSuccess
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response


@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    private val usersObserver: Observer<List<SearchUser>> = mock()
    private val errorObserver: Observer<String> = mock()
    private val loadingObserver: Observer<Boolean> = mock()
    private val navigateProfileObserver: Observer<String> = mock()
    private val searchRepository: SearchRepository = mock()

    @Before
    fun setup() {
        viewModel = SearchViewModel(searchRepository, TestDispatchers())
        viewModel.users.observeForever(usersObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.loading.observeForever(loadingObserver)
        viewModel.navigateToProfile.observeForever(navigateProfileObserver)
    }

    @Test
    fun searchUsers_success() {
        whenever(searchRepository.searchUsers(any())).thenReturn(
            CompletableDeferred(Response.success(searchResponseSuccess))
        )

        viewModel.searchUsers("query")

        val inOrder = inOrder(loadingObserver, usersObserver)

        inOrder.verify(loadingObserver).onChanged(true)
        inOrder.verify(usersObserver).onChanged(searchResponseSuccess.users)
        inOrder.verify(loadingObserver).onChanged(false)

        assertEquals(viewModel.users.value, searchResponseSuccess.users)
    }

    @Test
    fun searchUsers_failure() = runBlocking {
        val errorMessage = "error"
        val errorCode = 401
        whenever(searchRepository.searchUsers(any())).thenReturn(
            CompletableDeferred(mockError(errorCode, errorMessage))
        )

        viewModel.searchUsers("query")

        val inOrder = inOrder(loadingObserver, errorObserver)

        inOrder.verify(loadingObserver).onChanged(true)
        inOrder.verify(errorObserver).onChanged("$errorCode $errorMessage")
        inOrder.verify(loadingObserver).onChanged(false)
    }

    @Test
    fun check_showProfile() {
        val username = "username"

        viewModel.onUserClick(username)

        verify(navigateProfileObserver).onChanged(username)
    }
}