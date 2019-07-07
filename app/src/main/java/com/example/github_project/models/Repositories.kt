package com.example.github_project.models

import com.google.gson.annotations.SerializedName

class Repositories {

    @SerializedName("total_count")
    var totalCount: Int? = null

    @SerializedName("items")
    var items: List<Repository>? = null
}
