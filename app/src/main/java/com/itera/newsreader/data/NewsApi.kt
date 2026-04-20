package com.itera.newsreader.data

import io.ktor.client.call.*
import io.ktor.client.request.*

class NewsApi {
    // Kita gunakan NewsAPI. Nanti ganti "YOUR_API_KEY" dengan key aslimu dari newsapi.org
    private val baseUrl = "https://newsapi.org/v2/top-headlines?country=us&apiKey=0faefcb90a144faf99a182e7ca3332d9"

    suspend fun getTopHeadlines(): NewsResponse {
        return KtorClient.client.get(baseUrl).body()
    }
}