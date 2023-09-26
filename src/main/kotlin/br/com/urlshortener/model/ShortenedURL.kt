package br.com.urlshortener.model

data class ShortenedURL (
    val id: Long,
    val fullURL: String,
    val shortURL: String,
    val statistics: Statistics
)