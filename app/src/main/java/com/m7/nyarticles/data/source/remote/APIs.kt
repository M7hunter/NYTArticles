package com.m7.nyarticles.data.source.remote

import com.m7.nyarticles.BuildConfig
import com.m7.nyarticles.data.model.Article
import com.m7.nyarticles.data.model.MainRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIs {

    @GET("{section}/{num_of_articles}.json")
    suspend fun getArticlesBySection(
        @Path("section") section: String,
        @Path("num_of_articles") numOfArticles: String,
        @Query("api-key") apiKey: String = BuildConfig.API_KEY
    ): Response<MainRes<Article>>
}