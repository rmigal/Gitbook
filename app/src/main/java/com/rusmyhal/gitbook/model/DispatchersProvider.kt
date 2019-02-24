package com.rusmyhal.gitbook.model

import kotlinx.coroutines.CoroutineDispatcher


interface DispatchersProvider {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}