package br.com.urlshortener.model

import br.com.urlshortener.dto.StatisticsView
import java.sql.Timestamp

object StatisticsViewTest {
    fun build() = StatisticsView (
        id = 1,
        shortenedURLid = 1,
        fullURL = "https://tds.company/",
        clickdateTime = Timestamp(System.currentTimeMillis())
    )
}