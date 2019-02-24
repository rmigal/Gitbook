package com.rusmyhal.gitbook.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rusmyhal.gitbook.TestDispatchers
import com.rusmyhal.gitbook.model.data.server.entity.User
import com.rusmyhal.gitbook.model.repository.ProfileRepository
import com.rusmyhal.gitbook.repositories
import com.rusmyhal.gitbook.user
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response


class ProfileViewModelTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProfileViewModel

    private val profileRepository: ProfileRepository = mock()
    private val userObserver: Observer<User> = mock()
    private val loadingObserver: Observer<Boolean> = mock()
    private val errorObserver: Observer<String> = mock()
    private val repoNavigateObserver: Observer<String> = mock()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        viewModel = ProfileViewModel(profileRepository, TestDispatchers())

        viewModel.user.observeForever(userObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.loading.observeForever(loadingObserver)
        viewModel.repoNavigate.observeForever(repoNavigateObserver)
    }

    @Test
    fun fetchUser_success() {
        whenever(profileRepository.getUser(user.username)).thenReturn(
            CompletableDeferred(
                Response.success(
                    user
                )
            )
        )
        whenever(profileRepository.getUserRepos(user.username)).thenReturn(
            CompletableDeferred(
                Response.success(
                    repositories
                )
            )
        )

        viewModel.init(user.username)

        val inOrder = inOrder(loadingObserver, userObserver)
        inOrder.verify(loadingObserver).onChanged(true)
        inOrder.verify(userObserver).onChanged(user)
        inOrder.verify(loadingObserver).onChanged(false)

        assertEquals(repositories, user.repositories)
    }

    @Test
    fun fetchUser_failure() {
        val errorMessage = "Something went wrong"

        whenever(profileRepository.getUser(user.username)).thenThrow(
            RuntimeException(errorMessage)
        )
        whenever(profileRepository.getUserRepos(user.username)).thenThrow(
            RuntimeException(errorMessage)
        )

        viewModel.init(user.username)

        val inOrder = inOrder(loadingObserver, errorObserver)
        inOrder.verify(loadingObserver).onChanged(true)
        inOrder.verify(errorObserver).onChanged(errorMessage)
        inOrder.verify(loadingObserver).onChanged(false)

        assertEquals(errorMessage, viewModel.error.value)
    }

    @Test
    fun check_repoNavigate() {
        val repo = "repository"

        viewModel.onRepositoryClick(repo)

        verify(repoNavigateObserver).onChanged(repo)

        assertEquals(repo, viewModel.repoNavigate.value)
    }
}