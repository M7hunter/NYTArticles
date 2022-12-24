package com.m7.nyarticles.data.source.remote

import com.m7.nyarticles.ConnectivityHandler
import com.m7.nyarticles.data.model.Article
import com.m7.nyarticles.data.model.CallState
import com.m7.nyarticles.data.source.ArticlesDataSource
import java.lang.IllegalArgumentException
import java.net.ConnectException
import javax.inject.Inject

class ArticlesRemoteDataSource @Inject constructor(private val apIs: APIs): ArticlesDataSource {

    @Inject
    lateinit var connectivityHandler: ConnectivityHandler

    override suspend fun getArticlesBySection(
        section: String,
        numOfArticles: String
    ): CallState<List<Article>> {
        return try {
            if (connectivityHandler.isConnected()) {
                CallState.loading()

                apIs.getArticlesBySection(section, numOfArticles).let {
                    if (it.isSuccessful) {
                        it.body()?.let {
                            if (it.results.isNotEmpty()){
                                CallState.success(it.results)
                            } else {
                                CallState.error(Exception("no articles found"))
                            }
                        }
                            ?: CallState.error(NullPointerException("${it.code()}: ${it.message()}"))
                    } else {
                        CallState.error(IllegalArgumentException("${it.code()}: ${it.message()}"))
                    }
                }
            } else {
                CallState.error(ConnectException("no internet connection"))
            }
        } catch (e: Exception) {
            CallState.error(e)
        }
    }
}