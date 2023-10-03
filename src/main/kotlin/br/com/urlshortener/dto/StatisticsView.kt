package br.com.urlshortener.dto

import br.com.urlshortener.model.ShortenedURL
import java.sql.Timestamp

data class StatisticsView (
    var id: Long?,
    var clickdateTime: Timestamp,
    var shortenedURLid: Long?
)