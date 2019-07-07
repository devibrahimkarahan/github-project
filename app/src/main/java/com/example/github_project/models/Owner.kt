package com.example.github_project.models

import com.google.gson.annotations.SerializedName

class Owner {

    @SerializedName("login")
    var username: String? = null

    @SerializedName("id")
    var id: String? = null

    @SerializedName("avatar_url")
    var avatarUrl: String? = null

    @SerializedName("public_repos")
    var reposCount: Int? = null

    override fun toString(): String {
        return StringBuilder("Owner: ")
            .append("username: $username")
            .append(" / ")
            .append("id: $id")
            .append(" / ")
            .append("avatarUrl: $avatarUrl")
            .append(" / ")
            .append("reposCount: $reposCount")
            .toString()
    }
}