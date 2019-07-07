package com.example.github_project.services

import com.example.github_project.models.Owner
import com.example.github_project.models.Repository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IServiceUserDetails {

    @GET("users/{username}")
    fun getUserDetails(@Path("username") username: String): Observable<Owner>

    @GET("users/{username}/repos")
    fun getUserRepositories(
        @Path("username") username: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): Observable<List<Repository>>
}