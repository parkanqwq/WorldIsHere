package com.kalabukhov.app.worldishere.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.worldishere.R
import com.kalabukhov.app.worldishere.databinding.ItemGitHubUserBinding
import com.kalabukhov.app.worldishere.domain.GitHubRepoUserEntity
import com.squareup.picasso.Picasso

class AdapterGitHubUsersRepo :
    RecyclerView.Adapter<AdapterGitHubUsersRepo.MainViewHolder>() {

    private var gitHubUsersData: List<GitHubRepoUserEntity> = listOf()

    fun setUsersGitHub(data: List<GitHubRepoUserEntity>) {
        gitHubUsersData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MainViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_git_hub_user, parent, false) as View
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(gitHubUsersData[position])
    }

    override fun getItemCount() = gitHubUsersData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemGitHubUserBinding.bind(view)
        fun bind(gitHubRepoEntity: GitHubRepoUserEntity) = with(binding){
            nameTextView.text = gitHubRepoEntity.name
            Picasso
                .get()
                .load(gitHubRepoEntity.owner.avatarUrl)
                .into(imageProfileImageView)
            root.setOnClickListener {
                Toast.makeText(root.context,
                    root.context.resources.getString(R.string.size) +
                        gitHubRepoEntity.size + "\n" +
                        root.context.resources.getString(R.string.forks) +
                        gitHubRepoEntity.forksCount,
                Toast.LENGTH_SHORT).show()
            }
        }
    }
}