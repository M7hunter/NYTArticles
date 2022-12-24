package com.m7.nyarticles.data.source.repo

import com.m7.nyarticles.data.model.Article
import com.m7.nyarticles.data.model.CallState
import com.m7.nyarticles.data.source.ArticlesDataSource
import javax.inject.Inject

class DefaultArticlesRepo @Inject constructor(private val articlesDataSource: ArticlesDataSource) :
    ArticlesRepo {

    override suspend fun getArticlesBySection(
        section: String,
        numOfArticles: String
    ): CallState<List<Article>> {
        return articlesDataSource.getArticlesBySection(section, numOfArticles)
    }
}