package com.itera.newsreader.domain

import com.itera.newsreader.data.Article
import com.itera.newsreader.data.LocalCache
import com.itera.newsreader.data.NewsApi

interface NewsRepository {
    suspend fun getNews(): Result<List<Article>>
}

class NewsRepositoryImpl(
    private val localCache: LocalCache, // Menambahkan LocalCache
    private val api: NewsApi = NewsApi()
) : NewsRepository {
    override suspend fun getNews(): Result<List<Article>> {
        return try {
            // 1. Coba ambil data dari internet
            val response = api.getTopHeadlines()
            
            // 2. Jika berhasil, simpan ke memori lokal (cache)
            localCache.saveNews(response.articles)
            
            Result.success(response.articles)
        } catch (e: Exception) {
            // 3. Jika internet mati/error, coba ambil dari cache lokal
            val cachedNews = localCache.getCachedNews()
            
            if (cachedNews != null && cachedNews.isNotEmpty()) {
                Result.success(cachedNews) // Tampilkan data lama
            } else {
                Result.failure(e) // Error jika cache juga kosong
            }
        }
    }
}