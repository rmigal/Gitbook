package com.rusmyhal.gitbook

import android.app.Application
import com.rusmyhal.gitbook.di.gitBookModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class GitbookApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger()
            androidContext(this@GitbookApplication)
            modules(gitBookModules)
        }
    }
}