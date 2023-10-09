package br.com.urlshortener.model

import br.com.urlshortener.dto.ShortenedURLView

object ShortenedURLViewTest {
    fun build() = ShortenedURLView(
        id = 1,
        fullURL = "https://tds.company/",
        shortURL = "tdsCo1",
        timesAccessed = 0,
        dailyMeanSinceCreated = 0.0,
        statistics = arrayListOf(StatisticsViewTest.build())
    )
}