package com.example.github_project.contracts

import com.example.github_project.models.Repositories

interface IContractRepositories {

    interface View {
        fun init()
        fun initOnClickListeners()
        fun runSearch()
        fun showProgress()
        fun hideProgress()
        fun showError(err: String)
        fun showRepositories(repositories: Repositories)
        fun initPagination(totalCount: Int, itemsSize: Int)
        fun getPreviousRepositories()
        fun getNextRepositories()
    }

    interface Presenter {
        fun onCreate()
        fun btnSearchClicked()
        fun getRepositories(text: String, page: Int)
        fun setPagination(totalCount: Int, itemsSize: Int)
        fun btnPrevClicked()
        fun btnNextClicked()
    }

}