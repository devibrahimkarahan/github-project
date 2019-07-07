package com.example.github_project.contracts

import android.support.v7.widget.RecyclerView

interface IContractAdapterUserDetails {

    interface BaseView {
        fun initOnClickListeners()
    }

    interface UserDetailsView : BaseView {
        fun setAvatar(avatar_url: String)
        fun setUsername(username: String)
        fun setRepositoriesCount(count: Int)
    }

    interface RepositoryView : BaseView {
        fun setRepositoryName(name: String)
        fun setRepositoryDescription(description: String)
    }

    interface Presenter {
        fun start()
        fun onBindViewHolder(position: Int, view: RecyclerView.ViewHolder)
        fun getRepositoriesRowsCount(): Int
    }
}