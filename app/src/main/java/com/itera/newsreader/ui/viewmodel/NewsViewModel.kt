package com.itera.newsreader.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.itera.newsreader.domain.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Hapus = NewsRepositoryImpl() agar kita bisa memasukkannya secara manual
class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            repository.getNews()
                .onSuccess { articles ->
                    _uiState.value = NewsUiState.Success(articles)
                }
                .onFailure { error ->
                    _uiState.value = NewsUiState.Error(error.message ?: "Terjadi kesalahan")
                }
        }
    }
}

// Factory ini bertugas membuat ViewModel yang butuh parameter (Repository)
class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}