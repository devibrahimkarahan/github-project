package com.example.github_project.presenters

import com.example.github_project.contracts.IContractAdapterRepositories
import com.example.github_project.models.Owner
import com.example.github_project.models.Repository

class PresenterAdapterRepositories(_view: IContractAdapterRepositories.View?) : IContractAdapterRepositories.Presenter {

    var view = _view
    lateinit var repositories: List<Repository>

    override fun start() {
        view?.initClickListeners()
    }

    override fun onBindViewHolder(position: Int, view: IContractAdapterRepositories.View) {
        val repo: Repository = repositories[position]
        val owner: Owner = repo.owner!!
        view.setAvatar(owner.avatarUrl!!)
        view.setUsername(owner.username!!)
        view.setRepoName(repo.name!!)
    }

    override fun getRepositoriesRowsCount(): Int {
        return repositories.size
    }

    override fun clickedAvatar(position: Int) {
        val repo = repositories[position]
        val owner = repo.owner
        view?.runUserDetails(owner!!)
    }

    override fun clickedRepo(position: Int) {
        val repo = repositories[position]
        view?.runRepoDetails(repo)
    }
}