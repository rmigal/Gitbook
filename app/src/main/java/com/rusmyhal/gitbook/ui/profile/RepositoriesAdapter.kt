package com.rusmyhal.gitbook.ui.profile

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rusmyhal.gitbook.R
import com.rusmyhal.gitbook.model.data.server.entity.Repository
import com.rusmyhal.gitbook.utils.extensions.inflate
import kotlinx.android.synthetic.main.item_repository.view.*


class RepositoriesAdapter(private val clickListener: (url: String) -> Unit) :
    RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder>() {

    private val repositories = arrayListOf<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(parent.inflate(R.layout.item_repository))
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount() = repositories.size

    fun updateRepositories(newRepos: List<Repository>) {
        DiffUtil.calculateDiff(
            RepositoriesDiffCallback(
                repositories,
                newRepos
            )
        ).dispatchUpdatesTo(this)

        repositories.clear()
        repositories.addAll(newRepos)
    }

    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { clickListener(repositories[adapterPosition].link) }
        }

        fun bind(repository: Repository) = with(itemView) {
            repositoryNameTextView.text = repository.name

            repository.language?.let {
                repositoryLanguageTextView.visibility = View.VISIBLE
                repositoryLanguageTextView.text =
                    context.getString(R.string.item_repo_language, it)
            } ?: run {
                repositoryLanguageTextView.visibility = View.GONE
            }

            repositoryForksTextView.text = repository.forksCount.toString()
            repositoryStarsTextView.text = repository.starsCount.toString()
        }
    }
}