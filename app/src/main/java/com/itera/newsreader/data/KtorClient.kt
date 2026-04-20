package com.itera.newsreader.data

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Mengabaikan data JSON yang tidak kita perlukan
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }
}