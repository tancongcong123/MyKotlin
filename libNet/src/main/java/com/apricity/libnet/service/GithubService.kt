package com.apricity.libnet.service

import com.apricity.libnet.bean.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user:String?):Call<List<Repo>>
}