package com.m7.nyarticles.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.m7.nyarticles.R
import com.m7.nyarticles.data.model.CallState
import com.m7.nyarticles.databinding.ActivityMainBinding
import com.m7.nyarticles.ui.article.ArticleDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                mainViewModel.getArticles()

                val progressBar = ContentLoadingProgressBar(this@MainActivity)

                mainViewModel.articlesData.observe(this@MainActivity) {
                    when (it.status) {
                        CallState.Status.LOADING ->
                            progressBar.show()

                        CallState.Status.SUCCESS -> {
                            progressBar.hide()

                            it.data?.also {
                                rvArticles.apply {
                                    layoutManager = LinearLayoutManager(this@MainActivity)
                                    adapter = ArticleAdapter(it) {
                                        startActivity(
                                            Intent(
                                                this@MainActivity,
                                                ArticleDetailsActivity::class.java
                                            ).apply {
                                                putExtra(
                                                    ArticleDetailsActivity.EXTRA_ARTICLE_NAME,
                                                    it
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        CallState.Status.ERROR -> {
                            progressBar.hide()

                            it.ex?.also { ex ->
                                ex.printStackTrace()
                                Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }
            }
    }
}