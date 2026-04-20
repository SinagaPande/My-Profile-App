package com.itera.newsreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itera.newsreader.data.Article
import com.itera.newsreader.data.LocalCache
import com.itera.newsreader.domain.NewsRepositoryImpl
import com.itera.newsreader.ui.screen.NewsDetailScreen
import com.itera.newsreader.ui.screen.NewsListScreen
import com.itera.newsreader.ui.theme.NewsReaderTheme
import com.itera.newsreader.ui.viewmodel.NewsViewModel
import com.itera.newsreader.ui.viewmodel.NewsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 1. Siapkan Cache, API, dan Repository
        val localCache = LocalCache(applicationContext)
        val repository = NewsRepositoryImpl(localCache = localCache)
        val factory = NewsViewModelFactory(repository)

        setContent {
            NewsReaderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 2. Kirim factory ke UI
                    NewsApp(factory)
                }
            }
        }
    }
}

@Composable
fun NewsApp(factory: NewsViewModelFactory) {
    // Gunakan factory saat memanggil viewModel
    val viewModel: NewsViewModel = viewModel(factory = factory)
    
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    if (selectedArticle == null) {
        NewsListScreen(
            viewModel = viewModel,
            onArticleClick = { article -> selectedArticle = article }
        )
    } else {
        NewsDetailScreen(
            article = selectedArticle!!,
            onBackClick = { selectedArticle = null }
        )
    }
}