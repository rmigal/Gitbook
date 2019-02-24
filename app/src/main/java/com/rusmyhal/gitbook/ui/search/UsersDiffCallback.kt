package com.rusmyhal.gitbook.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.rusmyhal.gitbook.model.data.server.entity.SearchUser


class UsersDiffCallback(
    private val oldList: List<SearchUser>,
    private val newList: List<SearchUser>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }
}