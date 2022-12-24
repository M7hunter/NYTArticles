package com.m7.nyarticles.data.source

import com.m7.nyarticles.data.model.Article
import com.m7.nyarticles.data.model.CallState

interface ArticlesDataSource {

    suspend fun getArticlesBySection(
        section: String,
        numOfArticles: String
    ): CallState<List<Article>>
}