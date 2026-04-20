package com.itera.newsreader.ui.viewmodel

import com.itera.newsreader.data.Article

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}