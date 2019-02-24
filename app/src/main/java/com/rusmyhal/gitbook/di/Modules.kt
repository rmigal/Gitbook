package com.rusmyhal.gitbook.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rusmyhal.gitbook.BuildConfig
import com.rusmyhal.gitbook.model.AppDispatchers
import com.rusmyhal.gitbook.model.data.server.GithubApi
import com.rusmyhal.gitbook.model.repository.ProfileRepository
import com.rusmyhal.gitbook.model.repository.SearchRepository
import com.rusmyhal.gitbook.ui.profile.ProfileViewModel
import com.rusmyhal.gitbook.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(GithubApi::class.java)
    }

    single { SearchRepository(get()) }
    single { ProfileRepository(get()) }
}

private val viewModelModule = module {
    viewModel { SearchViewModel(get(), AppDispatchers()) }
    viewModel { ProfileViewModel(get(), AppDispatchers()) }
}

val gitBookModules = listOf(dataModule, viewModelModule)
