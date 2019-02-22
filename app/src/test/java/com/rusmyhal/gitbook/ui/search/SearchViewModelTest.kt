package com.rusmyhal.gitbook.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.rusmyhal.gitbook.TestDispatchers
import com.rusmyhal.gitbook.model.entity.User
import com.rusmyhal.gitbook.model.repository.SearchRepository
import com.rusmyhal.gitbook.searchResponseSuccess
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.After
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

    private val usersObserver: Observer<List<User>> = mock()
    private val errorObserver: Observer<String> = mock()
    private val searchRepository: SearchRepository = mock()
    private val responseBody: ResponseBody = mock()

    @Before
    fun setup() {
        viewModel = SearchViewModel(searchRepository, TestDispatchers())
        viewModel.users.observeForever(usersObserver)
        viewModel.error.observeForever(errorObserver)
    }

    @After
    fun after() {
        verifyNoMoreInteractions(usersObserver, errorObserver)
    }

    @Test
    fun test_searchUsers_success() = runBlocking {
        whenever(searchRepository.searchUsers(any())).thenReturn(
            CompletableDeferred(Response.success(searchResponseSuccess))
        )

        viewModel.searchUsers("query")

        verify(usersObserver).onChanged(searchResponseSuccess.users)

        assertEquals(viewModel.users.value, searchResponseSuccess.users)
    }

    @Test
    fun test_searchUsers_failure() = runBlocking {
        whenever(searchRepository.searchUsers(any())).thenReturn(
            CompletableDeferred(Response.error(403, responseBody))
        )

        viewModel.searchUsers("query")

        verify(errorObserver).onChanged("403 Response.error()")
    }
}