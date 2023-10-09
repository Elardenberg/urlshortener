package br.com.urlshortener.dto

data class ShortenedURLView (
    val id: Long?,
    val fullURL: String,
    val shortURL: String,
    val timesAccessed: Long,
    val dailyMeanSinceCreated: Double,
    val statistics: List<StatisticsView> = ArrayList()
)