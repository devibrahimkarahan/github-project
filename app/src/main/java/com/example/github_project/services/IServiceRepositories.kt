package com.example.github_project.services

import com.example.github_project.models.Repositories
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface IServiceRepositories {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") text: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): Observable<Repositories>
}