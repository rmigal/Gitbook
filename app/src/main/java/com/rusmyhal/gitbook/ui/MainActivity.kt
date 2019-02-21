package com.rusmyhal.gitbook.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rusmyhal.gitbook.R
import com.rusmyhal.gitbook.ui.search.SearchFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, SearchFragment())
                .commitNow()
        }
    }
}