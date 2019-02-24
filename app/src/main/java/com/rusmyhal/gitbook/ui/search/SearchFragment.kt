package com.rusmyhal.gitbook.ui.search

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rusmyhal.gitbook.R
import com.rusmyhal.gitbook.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.viewModel


class SearchFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_search

    private val viewModel: SearchViewModel by viewModel()
    private val usersAdapter: UsersAdapter by lazy {
        UsersAdapter { username -> viewModel.onUserClick(username) }
    }

    private lateinit var callback: OnUserSelectedListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = context as OnUserSelectedListener
        } catch (e: ClassCastException) {
            throw RuntimeException("$context should implement ${OnUserSelectedListener::toString}")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()
        setupUsersRecycler()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchUsers(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        subscribe()
    }

    private fun updateToolbar() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }

    private fun setupUsersRecycler() {
        usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun subscribe() = with(viewModel) {
        users.observe(viewLifecycleOwner, Observer { users ->
            users?.let {
                usersAdapter.updateData(it)
            }
        })

        error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(
                context,
                error ?: getString(R.string.error_default),
                Toast.LENGTH_SHORT
            ).show()
        })

        loading.observe(viewLifecycleOwner, Observer { isShow ->
            if (isShow) progress.show() else progress.hide()
        })

        navigateToProfile.observe(viewLifecycleOwner, Observer { username ->
            callback.onUserSelected(username)
        })
    }

    interface OnUserSelectedListener {
        fun onUserSelected(username: String)
    }

    companion object {

        const val TAG = "SearchFragment"
    }
}