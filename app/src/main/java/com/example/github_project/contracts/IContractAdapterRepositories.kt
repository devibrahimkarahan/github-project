package com.example.github_project.contracts

import com.example.github_project.models.Owner
import com.example.github_project.models.Repository

interface IContractAdapterRepositories {

    interface View {
        fun initClickListeners()
        fun setAvatar(avatarUrl: String)
        fun setUsername(username: String)
        fun setRepoName(repoName: String)
        fun runUserDetails(owner: Owner)
        fun runRepoDetails(repo: Repository)
    }

    interface Presenter {
        fun start()
        fun onBindViewHolder(position: Int, view: View)
        fun getRepositoriesRowsCount(): Int
        fun clickedAvatar(position: Int)
        fun clickedRepo(position: Int)
    }
}