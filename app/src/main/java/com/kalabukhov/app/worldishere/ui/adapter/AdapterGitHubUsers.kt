package com.kalabukhov.app.worldishere.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.worldishere.R
import com.kalabukhov.app.worldishere.databinding.ItemGitHubUserBinding
import com.kalabukhov.app.worldishere.domain.GitHubRepoEntity
import com.kalabukhov.app.worldishere.ui.less5.GitHubTab
import com.squareup.picasso.Picasso

class AdapterGitHubUsers (
    private var onItemViewClickListener: GitHubTab.OnItemViewClickListener?
    ) :
    RecyclerView.Adapter<AdapterGitHubUsers.MainViewHolder>() {

    private var gitHubUsersData: List<GitHubRepoEntity> = listOf()

    fun setUsersGitHub(data: List<GitHubRepoEntity>) {
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
        fun bind(gitHubRepoEntity: GitHubRepoEntity) = with(binding){
            nameTextView.text = gitHubRepoEntity.name
            Picasso
                .get()
                .load(gitHubRepoEntity.avatarUrl)
                .into(imageProfileImageView)
            root.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(gitHubRepoEntity)
            }
        }
    }
}