package com.rusmyhal.gitbook.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rusmyhal.gitbook.R
import com.rusmyhal.gitbook.model.data.server.entity.Repository
import com.rusmyhal.gitbook.model.data.server.entity.User
import com.rusmyhal.gitbook.ui.base.BaseFragment
import com.rusmyhal.gitbook.utils.picasso.CircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.viewModel


class ProfileFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_profile

    private val viewModel: ProfileViewModel by viewModel()

    private val repositoriesAdapter: RepositoriesAdapter by lazy {
        RepositoriesAdapter { viewModel.onRepositoryClick(it) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()
        setupRepositoriesRecycler()

        requireNotNull(arguments?.getString(EXTRA_USERNAME))
        val username = arguments!!.getString(EXTRA_USERNAME)!!
        viewModel.init(username)

        subscribe()
    }

    private fun updateToolbar() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupRepositoriesRecycler() {
        repositoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repositoriesAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun subscribe() = with(viewModel) {
        user.observe(viewLifecycleOwner, Observer { user ->
            showProfileData(user)
            showRepositories(user.repositories)
        })

        error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })

        loading.observe(viewLifecycleOwner, Observer { isShow ->
            if (isShow) progress.show() else progress.hide()
        })

        repoNavigate.observe(viewLifecycleOwner, Observer { link ->
            context?.packageManager?.let { manager ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                if (intent.resolveActivity(manager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.profile_no_intent_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun showRepositories(repositories: List<Repository>) {
        if (repositories.isNotEmpty()) {
            repositoriesAdapter.updateRepositories(repositories)
            if (R.id.repositoriesRecyclerView == switcher.nextView.id) {
                switcher.showNext()
            }
        } else {
            if (R.id.emptyTextView == switcher.nextView.id) {
                switcher.showNext()
            }
        }
    }

    private fun showProfileData(user: User) = with(user) {
        Picasso.get()
            .load(avatarUrl)
            .transform(CircleTransformation())
            .into(avatar)

        nameTextView.text = displayName

        followersTextView.text =
            createStatSpannable(getString(R.string.profile_followers), followers.toString())
        followingTextView.text =
            createStatSpannable(getString(R.string.profile_following), following.toString())
    }

    private fun createStatSpannable(title: String, amount: String): Spannable {
        val spannable = SpannableString("$amount\n$title")
        spannable.setSpan(
            RelativeSizeSpan(STAT_SIZE_PROPORTION),
            0,
            amount.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        return spannable
    }

    companion object {

        private const val STAT_SIZE_PROPORTION = 1.5f
        private const val EXTRA_USERNAME = "extra_username"
        const val TAG = "ProfileFragment"

        fun newInstance(username: String): ProfileFragment {
            return ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USERNAME, username)
                }
            }
        }
    }
}