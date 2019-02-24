package com.rusmyhal.gitbook.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rusmyhal.gitbook.R
import com.rusmyhal.gitbook.ui.profile.ProfileFragment
import com.rusmyhal.gitbook.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SearchFragment.OnUserSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, SearchFragment())
                .commitNow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    override fun onUserSelected(username: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, ProfileFragment.newInstance(username))
            .addToBackStack(ProfileFragment.TAG)
            .commit()
    }
}