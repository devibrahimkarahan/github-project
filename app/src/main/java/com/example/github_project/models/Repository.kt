package com.example.github_project.models

import com.google.gson.annotations.SerializedName

class Repository {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("private")
    var private: Boolean? = null

    @SerializedName("owner")
    var owner: Owner? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("stargazers_count")
    var stargazersCount: Int? = null

    @SerializedName("forks_count")
    var forksCount: Int? = null

    @SerializedName("default_branch")
    var defaultBranch: String? = null

    @SerializedName("open_issues_count")
    var openIssuesCount: Int? = null

    @SerializedName("language")
    var language: String? = null

}