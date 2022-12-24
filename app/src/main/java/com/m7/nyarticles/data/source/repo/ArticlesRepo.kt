package com.m7.nyarticles.data.source.repo

import com.m7.nyarticles.data.model.Article
import com.m7.nyarticles.data.model.CallState

interface ArticlesRepo {

    suspend fun getArticlesBySection(
        section: String,
        numOfArticles: String
    ): CallState<List<Article>>
}