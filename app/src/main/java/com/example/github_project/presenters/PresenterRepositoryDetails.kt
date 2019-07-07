package com.example.github_project.presenters

import com.example.github_project.contracts.IContractRepositoryDetails

class PresenterRepositoryDetails(_view: IContractRepositoryDetails.View) : IContractRepositoryDetails.Presenter {

    private val view = _view

    override fun onCreate() {
        view.init()
        view.initOnClickListeners()
    }

    override fun avatarClicked() {
        view.runUserDetails()
    }
}