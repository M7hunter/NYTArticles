package com.m7.nyarticles.data.source.repo

import com.google.common.truth.Truth.assertThat
import com.m7.nyarticles.data.model.Article
import com.m7.nyarticles.data.model.CallState
import com.m7.nyarticles.data.source.repo.remote.FakeArticlesDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultArticlesRepoTest {

    private lateinit var fakeArticlesDataSource: FakeArticlesDataSource
    private lateinit var defaultArticlesRepo: DefaultArticlesRepo

    @Before
    fun setup() {
        fakeArticlesDataSource = FakeArticlesDataSource()
        val fakeArticlesList = mutableListOf<Article>()
        for (i in 0..5) {
            fakeArticlesList.add(Article(i.toLong(), "$i", "$i", "$i", "$i", null))
        }
        fakeArticlesDataSource.setArticles(fakeArticlesList)

        defaultArticlesRepo = DefaultArticlesRepo(fakeArticlesDataSource)
    }

    @Test
    fun `getArticlesBySection without connection, returns error status`() = runTest {
        fakeArticlesDataSource.haveNetWorkConnection(false)
        val callState = defaultArticlesRepo.getArticlesBySection("", "")

        assertThat(callState.status).isEqualTo(CallState.Status.ERROR)
    }

    @Test
    fun `getArticlesBySection with null data, returns error status`() = runTest {
        fakeArticlesDataSource.setArticles(null)
        val callState = defaultArticlesRepo.getArticlesBySection("", "")

        assertThat(callState.status).isEqualTo(CallState.Status.ERROR)
    }

    @Test
    fun `getArticlesBySection with empty list, returns error status`() = runTest {
        fakeArticlesDataSource.setArticles(emptyList())
        val callState = defaultArticlesRepo.getArticlesBySection("", "")

        assertThat(callState.status).isEqualTo(CallState.Status.ERROR)
    }

    @Test
    fun `getArticlesBySection with connection and non-null non-empty list, returns success status`() =
        runTest {
            val callState = defaultArticlesRepo.getArticlesBySection("", "")

            assertThat(callState.status).isEqualTo(CallState.Status.SUCCESS)
        }
}