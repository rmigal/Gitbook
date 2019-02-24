package com.rusmyhal.gitbook.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rusmyhal.gitbook.model.DispatchersProvider
import com.rusmyhal.gitbook.model.data.server.entity.User
import com.rusmyhal.gitbook.model.repository.ProfileRepository
import com.rusmyhal.gitbook.utils.arch.SingleLiveEvent
import kotlinx.coroutines.*


class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext = dispatchersProvider.main + job

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = SingleLiveEvent<String>()
    val error: LiveData<String>
        get() = _error

    private val _repoNavigate = SingleLiveEvent<String>()
    val repoNavigate: LiveData<String>
        get() = _repoNavigate

    override fun onCleared() {
        super.onCleared()
        job.cancelChildren()
    }

    fun init(username: String) = launch {
        if (_user.value != null) return@launch

        _loading.value = true

        try {
            val result = withContext(dispatchersProvider.io) {
                val requestUser = profileRepository.getUser(username)
                val requestRepos = profileRepository.getUserRepos(username)

                requestUser.await().body()!! + requestRepos.await().body()!!
            }
            _user.value = result
        } catch (e: Exception) {
            e.printStackTrace()
            _error.value = e.message
        }

        _loading.value = false
    }

    fun onRepositoryClick(link: String) {
        _repoNavigate.value = link
    }

    companion object {

        const val TAG = "ProfileViewModel"
    }

}