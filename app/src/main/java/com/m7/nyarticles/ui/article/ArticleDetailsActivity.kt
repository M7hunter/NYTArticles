package com.m7.nyarticles.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.m7.nyarticles.R
import com.m7.nyarticles.databinding.ActivityArticleDetailsBinding

class ArticleDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ARTICLE_NAME = "Article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityArticleDetailsBinding>(
            this,
            R.layout.activity_article_details
        )
            .apply {
                article = intent.extras?.getParcelable(EXTRA_ARTICLE_NAME)
            }
    }
}