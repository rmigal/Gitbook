package com.rusmyhal.gitbook.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rusmyhal.gitbook.R
import com.rusmyhal.gitbook.model.entity.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*


class UsersAdapter(
    private val userClickListener: (String) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val users = arrayListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    fun updateData(newUsers: List<User>) {
        DiffUtil
            .calculateDiff(UsersDiffCallback(users, newUsers))
            .dispatchUpdatesTo(this)

        users.clear()
        users.addAll(newUsers)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { userClickListener(users[adapterPosition].login) }
        }

        fun bind(user: User) = with(itemView) {
            Picasso.get()
                .load(user.avatarUrl)
                .into(avatarImageView)
            usernameTextView.text = user.login
        }
    }
}