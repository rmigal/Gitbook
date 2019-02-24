package com.rusmyhal.gitbook.ui.profile

import androidx.recyclerview.widget.DiffUtil
import com.rusmyhal.gitbook.model.data.server.entity.Repository


class RepositoriesDiffCallback(
    private val oldList: List<Repository>,
    private val newList: List<Repository>
) : DiffUtil.Callback() {

    override fun getNewListSize() = newList.size

    override fun getOldListSize() = oldList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }
}