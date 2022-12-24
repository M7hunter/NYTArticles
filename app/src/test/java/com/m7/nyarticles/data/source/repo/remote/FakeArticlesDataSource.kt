package com.m7.nyarticles.data.source.repo.remote

import com.m7.nyarticles.data.model.Article
import com.m7.nyarticles.data.model.CallState
import com.m7.nyarticles.data.source.ArticlesDataSource
import java.net.ConnectException

class FakeArticlesDataSource : ArticlesDataSource {

    private var haveNetWorkConnection = true
    private var articles: List<Article>? = null

    fun haveNetWorkConnection(value: Boolean) {
        haveNetWorkConnection = value
    }

    fun setArticles(value: List<Article>?) {
        articles = value
    }

    override suspend fun getArticlesBySection(
        section: String,
        numOfArticles: String
    ): CallState<List<Article>> {
        return if (haveNetWorkConnection) {
            articles?.let {
                if (it.isNotEmpty()) {
                    CallState.success(it)
                } else {
                    CallState.error(Exception("no articles found"))
                }
            }
                ?: CallState.error(NullPointerException())
        } else {
            CallState.error(ConnectException("no internet connection"))
        }
    }
}