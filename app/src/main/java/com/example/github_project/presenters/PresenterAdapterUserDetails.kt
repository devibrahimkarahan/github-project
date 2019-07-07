package com.example.github_project.presenters

import android.support.v7.widget.RecyclerView
import com.example.github_project.contracts.IContractAdapterUserDetails
import com.example.github_project.models.Owner
import com.example.github_project.models.Repository
import com.example.github_project.models.UserDetailsItem

class PresenterAdapterUserDetails(_view: IContractAdapterUserDetails.BaseView?) :
    IContractAdapterUserDetails.Presenter {

    var view = _view
    lateinit var itemsList: List<UserDetailsItem>

    override fun start() {
        view?.initOnClickListeners()
    }

    override fun onBindViewHolder(position: Int, view: RecyclerView.ViewHolder) {
        if (view is IContractAdapterUserDetails.UserDetailsView) {
            val user: Owner = itemsList[position].owner!!
            (view as IContractAdapterUserDetails.UserDetailsView).apply {
                this.setAvatar(user.avatarUrl!!)
                this.setUsername(user.username!!)
                this.setRepositoriesCount(user.reposCount!!)
            }
        } else {
            val repository: Repository = itemsList[position].repository!!
            (view as IContractAdapterUserDetails.RepositoryView).apply {
                this.setRepositoryName(repository.name!!)
                repository.description?.let { this.setRepositoryDescription(it) }
            }
        }
    }

    override fun getRepositoriesRowsCount(): Int {
        return itemsList.size
    }
}