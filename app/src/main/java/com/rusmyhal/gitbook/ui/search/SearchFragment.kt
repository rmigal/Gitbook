package com.rusmyhal.gitbook.ui.search

import com.rusmyhal.gitbook.R
import com.rusmyhal.gitbook.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.viewModel


class SearchFragment : BaseFragment() {

    private val viewModel: SearchViewModel by viewModel()

    override val layoutRes = R.layout.fragment_search
}