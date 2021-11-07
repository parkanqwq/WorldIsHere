package com.kalabukhov.app.worldishere.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.worldishere.R
import com.kalabukhov.app.worldishere.databinding.ItemGitHubUserBinding
import com.kalabukhov.app.worldishere.domain.GitHubRepoEntity
import com.kalabukhov.app.worldishere.domain.entity.UserEntity
import com.kalabukhov.app.worldishere.ui.less5.GitHubTab
import com.squareup.picasso.Picasso

class AdapterRoomUsers (
    private var onItemViewClickListener: GitHubTab.OnItemViewClickListener?
) :
    RecyclerView.Adapter<AdapterRoomUsers.MainViewHolder>() {

    private var gitHubUsersData: List<UserEntity> = listOf()

    fun setUsersGitHubRoom(data: List<UserEntity>) {
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
        fun bind(userEntity: UserEntity) = with(binding){
            nameTextView.text = userEntity.name
            Picasso
                .get()
                .load(userEntity.imageUrl)
                .into(imageProfileImageView)
            root.setOnClickListener {
                val gitHubRepoEntity = GitHubRepoEntity(userEntity.name, userEntity.imageUrl)
                onItemViewClickListener?.onItemViewClick(gitHubRepoEntity)
            }
        }
    }
}