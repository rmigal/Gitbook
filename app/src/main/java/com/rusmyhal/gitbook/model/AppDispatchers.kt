package com.rusmyhal.gitbook.model

import kotlinx.coroutines.Dispatchers


class AppDispatchers : DispatchersProvider {
    override val default = Dispatchers.Default
    override val io = Dispatchers.IO
    override val main = Dispatchers.Main
}