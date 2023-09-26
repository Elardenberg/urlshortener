package br.com.urlshortener.service

import br.com.urlshortener.dto.AtualizacaoStatisticsDTO
import br.com.urlshortener.dto.NovaURLDTO
import br.com.urlshortener.model.ShortenedURL
import br.com.urlshortener.model.Statistics
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class ShortenedURLService(
    private var urls: List<ShortenedURL> = ArrayList(),
    private var listOfStatistics: List<Statistics> = ArrayList()) {
    fun listarURL(): List<ShortenedURL> {
        return urls
    }

    fun procurarURL(text: String): List<ShortenedURL> {
        val foundURLs = arrayListOf<ShortenedURL>()
        for (url in urls) {
            if (url.fullURL.contains(text)) {
                foundURLs.add(url)
            }
        }
        return foundURLs
    }

    fun cadastrarURL(shortenedURLDTO: NovaURLDTO) {
        listOfStatistics = listOfStatistics.plus(Statistics(
                id = urls.size.toLong() + 1,
                shortURLid = urls.size.toLong() + 1,
                timesClicked = 0
        ))
        urls = urls.plus(ShortenedURL(
            id = urls.size.toLong() + 1,
            fullURL = shortenedURLDTO.fullURL,
            shortURL = encurtarURL(),
            statistics = listOfStatistics.last()
        ))
    }

    fun encurtarURL(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val shortURL = (1..6).map { charset.random() }.joinToString("")
        return shortURL
    }

    fun getListOfStatistics(): List<Statistics> {
        return listOfStatistics
    }

    fun deletar(id: Long) {
        val url = urls.stream().filter{
            it.id == id
        }.findFirst().get()
        urls = urls.minus(url)
        val statistics = listOfStatistics.stream().filter{
            it.shortURLid == id
        }.findFirst().get()
        listOfStatistics = listOfStatistics.minus(statistics)
    }
}