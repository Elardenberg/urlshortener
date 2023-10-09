package br.com.urlshortener.dto

import java.sql.Timestamp

data class StatisticsView (
    var id: Long?,
    var shortenedURLid: Long?,
    var fullURL: String,
    var clickdateTime: Timestamp
)