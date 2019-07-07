package com.example.github_project.contracts

import com.example.github_project.models.Owner
import com.example.github_project.models.Repository

interface IContractUserDetails {

    interface View {
        fun init()
        fun initOnClickListeners()
        fun showProgress()
        fun hideProgress()
        fun showError(err: String)
        fun setUserDetails(user: Owner)
        fun showItems(repositories: List<Repository>)
        fun initPagination(repositoriesSize: Int)
        fun getPrevRepositories()
        fun getNextRepositories()
    }

    interface Presenter {
        fun onCreate()
        fun getUserDetails(user: Owner)
        fun getUserRepositories(user: Owner, per_page: Int, page: Int)
        fun setPagination(repositoriesSize: Int)
        fun btnPrevClicked()
        fun btnNextClicked()
    }

}