package br.com.urlshortener.model

object ShortenedURLTest {
    fun build() = ShortenedURL(
        id = 1,
        fullURL = "https://tds.company/",
        shortURL = "tdsCo1",
        statistics = ArrayList()
        )
}