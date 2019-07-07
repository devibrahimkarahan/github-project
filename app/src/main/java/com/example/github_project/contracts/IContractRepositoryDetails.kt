package com.example.github_project.contracts

interface IContractRepositoryDetails {

    interface View {
        fun init()
        fun initOnClickListeners()
        fun runUserDetails()
    }

    interface Presenter {
        fun onCreate()
        fun avatarClicked()
    }

}