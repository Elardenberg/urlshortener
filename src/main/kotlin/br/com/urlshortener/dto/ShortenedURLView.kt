package br.com.urlshortener.dto

data class ShortenedURLView (
    val id: Long?,
    val fullURL: String,
    val shortURL: String,
    val timesAccessed: Long,
    val statistics: List<StatisticsView> = ArrayList()
)