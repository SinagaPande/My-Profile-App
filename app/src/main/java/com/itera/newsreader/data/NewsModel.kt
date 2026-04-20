package com.itera.newsreader.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val articles: List<Article>
)

@Serializable
data class Article(
    val title: String? = null,
    val description: String? = null,
    val urlToImage: String? = null
)