package br.com.urlshortener.model

import java.sql.Timestamp

object StatisticsTest {
    fun build() = Statistics (
        id = 1,
        clickdateTime = Timestamp(System.currentTimeMillis()),
        shortenedURL = ShortenedURLTest.build()
        )
}