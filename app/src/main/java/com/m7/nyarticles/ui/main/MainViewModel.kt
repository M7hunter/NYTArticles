package com.m7.nyarticles.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m7.nyarticles.data.model.Article
import com.m7.nyarticles.data.model.CallState
import com.m7.nyarticles.data.source.repo.ArticlesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val articlesRepo: ArticlesRepo) : ViewModel() {

    private val _articlesData = MutableLiveData<CallState<List<Article>>>()
    val articlesData: LiveData<CallState<List<Article>>> = _articlesData

    fun getArticles() {
        viewModelScope.launch((CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            _articlesData.postValue(CallState.error(throwable as Exception))
        })) {
            _articlesData.postValue(articlesRepo.getArticlesBySection("all-sections", "7"))
        }
    }
}