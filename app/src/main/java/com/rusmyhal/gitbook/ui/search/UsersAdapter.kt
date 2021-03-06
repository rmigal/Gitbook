package com.rusmyhal.gitbook.ui.search

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rusmyhal.gitbook.R
import com.rusmyhal.gitbook.model.data.server.entity.SearchUser
import com.rusmyhal.gitbook.utils.extensions.inflate
import com.rusmyhal.gitbook.utils.picasso.CircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*


class UsersAdapter(
    private val userClickListener: (String) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val users = arrayListOf<SearchUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(parent.inflate(R.layout.item_user))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    fun updateData(newUsers: List<SearchUser>) {
        DiffUtil
            .calculateDiff(UsersDiffCallback(users, newUsers))
            .dispatchUpdatesTo(this)

        users.clear()
        users.addAll(newUsers)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { userClickListener(users[adapterPosition].username) }
        }

        fun bind(user: SearchUser) = with(itemView) {
            Picasso.get()
                .load(user.avatarUrl)
                .transform(CircleTransformation())
                .into(avatarImageView)
            usernameTextView.text = user.username
        }
    }
}