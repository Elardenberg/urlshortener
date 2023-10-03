package br.com.urlshortener.service

import br.com.urlshortener.dto.NovaURLDTO
import br.com.urlshortener.dto.ShortenedURLView
import br.com.urlshortener.exception.NotFoundException
import br.com.urlshortener.model.ShortenedURL
import br.com.urlshortener.model.Statistics
import br.com.urlshortener.repository.ShortenedURLRepository
import br.com.urlshortener.repository.StatisticsRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ShortenedURLService(
    private val urls: ShortenedURLRepository,
    private val listOfStatistics: StatisticsRepository,
    private val notFoundMessage: String = "URL não encontrada!",
    private val statisticsService: StatisticsService) {
    fun listarURL(nomeURL: String?,
                  paginacao: Pageable
    ): Iterable<ShortenedURLView> {
        val url = if (nomeURL == null) {
            urls.findAll(paginacao)
        } else {
            urls.findByFullURL(nomeURL, paginacao)
        }

        return url.stream().map { ShortenedURLView(
            id = it.id,
            fullURL = it.fullURL,
            shortURL = it.shortURL,
            statistics = statisticsService.listarStatisticsById(it.id)
        ) }.collect(Collectors.toList())
    }

    fun procurarURL(text: String): List<ShortenedURL> {
        val foundURLs = arrayListOf<ShortenedURL>()
        urls.findAll().forEach{
            if (it.fullURL.contains(text)) foundURLs.add(it)
        }
        foundURLs.takeIf { it.isNotEmpty() } ?: throw NotFoundException(notFoundMessage)
        return foundURLs
    }

    fun cadastrarURL(shortenedURLDTO: NovaURLDTO) {
        val url = ShortenedURL(
            fullURL = shortenedURLDTO.fullURL,
            shortURL = encurtarURL()
        )
        urls.save(url)
    }

    fun encurtarURL(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val shortURL = (1..6).map { charset.random() }.joinToString("")
        return shortURL
    }

    fun deletar(id: Long) {
        val url = urls.findById(id).orElseThrow{NotFoundException(notFoundMessage)}
        val listOfIDsToBeDeleted = arrayListOf<Long?>()
        listOfStatistics.findAll().forEach{
            if (it.shortenedURL.id == url.id) listOfIDsToBeDeleted.add(it.id)
        }
        listOfStatistics.deleteAllById(listOfIDsToBeDeleted)
        urls.delete(url)
    }
}