package com.example.github_project.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.github_project.R
import com.example.github_project.contracts.IContractAdapterUserDetails
import com.example.github_project.models.UserDetailsItem
import com.example.github_project.presenters.PresenterAdapterUserDetails
import com.example.github_project.utility.GlideUtils

class AdapterUserDetails(
    _context: Context,
    _itemsList: List<UserDetailsItem>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context = _context
    private var presenterAdapterUserDetails = PresenterAdapterUserDetails(null).apply { this.itemsList = _itemsList }

    companion object {
        const val TYPE_USER_DETAILS = 0
        const val TYPE_REPOSITORY = 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return if (viewType == TYPE_USER_DETAILS) {
            ViewHolderUserDetails(
                inflater.inflate(R.layout.item_user_details, viewGroup, false),
                presenterAdapterUserDetails
            )
        } else {
            ViewHolderRepository(
                inflater.inflate(R.layout.item_user_repository, viewGroup, false),
                presenterAdapterUserDetails
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_USER_DETAILS else TYPE_REPOSITORY
    }

    override fun getItemCount(): Int {
        return presenterAdapterUserDetails.getRepositoriesRowsCount()
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        presenterAdapterUserDetails.onBindViewHolder(position, viewHolder)
    }

    class ViewHolderUserDetails(
        _itemView: View,
        _presenterAdapterUserDetails: PresenterAdapterUserDetails
    ) :
        RecyclerView.ViewHolder(_itemView), IContractAdapterUserDetails.UserDetailsView {

        private val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        private val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        private val tvReposCount: TextView = itemView.findViewById(R.id.tv_repos_count)
        private val presentAdapterUserDetails = _presenterAdapterUserDetails.apply {
            view = this@ViewHolderUserDetails
        }

        init {
            presentAdapterUserDetails.start()
        }

        override fun initOnClickListeners() {
        }

        override fun setAvatar(avatar_url: String) {
            GlideUtils.imageLoad(itemView.context, avatar_url, ivAvatar)
        }

        override fun setUsername(username: String) {
            tvUsername.text = username
        }

        override fun setRepositoriesCount(count: Int) {
            tvReposCount.text =
                StringBuilder(tvReposCount.context.getString(R.string.repositories_count))
                    .append(count)
                    .toString()
        }
    }

    class ViewHolderRepository(
        _itemView: View,
        _presenterAdapterUserDetails: PresenterAdapterUserDetails
    ) :
        RecyclerView.ViewHolder(_itemView), IContractAdapterUserDetails.RepositoryView {

        private val tvRepoName: TextView = itemView.findViewById(R.id.tv_repoName)
        private val tvRepoDescription: TextView = itemView.findViewById(R.id.tv_repoDescription)
        private val presenterAdapterUserDetails = _presenterAdapterUserDetails.apply {
            view = this@ViewHolderRepository
        }

        init {
            presenterAdapterUserDetails.start()
        }

        override fun initOnClickListeners() {
        }

        override fun setRepositoryName(name: String) {
            tvRepoName.text = name
        }

        override fun setRepositoryDescription(description: String) {
            tvRepoDescription.text = description
        }
    }
}