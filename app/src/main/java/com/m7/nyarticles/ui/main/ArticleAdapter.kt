package com.m7.nyarticles.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.m7.nyarticles.R
import com.m7.nyarticles.data.model.Article
import com.m7.nyarticles.databinding.ItemArticleBinding

class ArticleAdapter(
    private val articles: List<Article>,
    private val onArticleClicked: (article: Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun getItemCount() = articles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate<ItemArticleBinding?>(
                LayoutInflater.from(parent.context),
                R.layout.item_article,
                parent,
                false
            ).apply {
                root.setOnClickListener {
                    article?.also(onArticleClicked)
                }
            }
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    inner class ViewHolder(private val itemBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(itemData: Article) {
            itemBinding.apply {
                article = itemData
            }
        }
    }
}