package br.com.urlshortener.service

import br.com.urlshortener.dto.AtualizacaoStatisticsDTO
import br.com.urlshortener.dto.NovaURLDTO
import br.com.urlshortener.dto.ShortenedURLView
import br.com.urlshortener.exception.NotFoundException
import br.com.urlshortener.model.ShortenedURL
import br.com.urlshortener.repository.ShortenedURLRepository
import br.com.urlshortener.repository.StatisticsRepository
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import jakarta.servlet.http.HttpServletRequest
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


@Service
class ShortenedURLService(
    private val urls: ShortenedURLRepository,
    private val listOfStatistics: StatisticsRepository,
    private val notFoundMessage: String = "URL não encontrada!",
    private val statisticsService: StatisticsService,
    private val request: HttpServletRequest) {
    fun listarURL(paginacao: Pageable
    ): Iterable<ShortenedURLView> {
        val url = urls.findAll(paginacao)
        return mapView(ArrayList(url.content))
    }

    fun calcularDiferencaEmDias(timestamp1: Timestamp, timestamp2: Timestamp): Int {
        val date1 = Date(timestamp1.time)
        val date2 = Date(timestamp2.time)

        val diffInMillis = date1.time - date2.time

        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS).toInt()
    }

    fun mapView(l: ArrayList<ShortenedURL>): ArrayList<ShortenedURLView> {
        return ArrayList(l.stream().map { ShortenedURLView(
            id = it.id,
            fullURL = it.fullURL,
            shortURL = getShortURL(it.shortURL),
            timesAccessed = statisticsService.listarStatisticsById(it.id).size.toLong(),
            dailyMeanSinceCreated = statisticsService.listarStatisticsById(it.id).size.toDouble()/calcularDiferencaEmDias(Timestamp(System.currentTimeMillis()), statisticsService.listarStatisticsById(it.id).first().clickdateTime),
            statistics = statisticsService.listarStatisticsById(it.id)
        ) }.collect(Collectors.toList()))
    }

    fun procurarURL(text: String): List<ShortenedURLView> {
        val foundURLs = arrayListOf<ShortenedURL>()
        urls.findAll().forEach{
            if (it.fullURL.contains(text)) foundURLs.add(it)
        }
        foundURLs.takeIf { it.isNotEmpty() } ?: throw NotFoundException(notFoundMessage)
        return mapView(foundURLs)
    }

    fun cadastrarURL(shortenedURLDTO: NovaURLDTO) {
        val url = ShortenedURL(
            fullURL = shortenedURLDTO.fullURL,
            shortURL = this.encurtarURL()
        )
        urls.save(url)
    }

    fun encurtarURL(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..6).map { charset.random() }.joinToString("")
    }

    fun getShortURL(shortURL: String): String {
        val protocol = request.scheme // http or https
        val hostname = request.serverName
        val port = request.serverPort

        return "$protocol://$hostname:$port/$shortURL"
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

    fun acessarPorShortURL(shortURL: String): ResponseEntity<String> {
        val headers = HttpHeaders()
        urls.findAll().forEach{
            if (it.shortURL == shortURL) {
                statisticsService.clicar(AtualizacaoStatisticsDTO(
                    id = it.id ?: 0
                ))
                headers.add("Location", it.fullURL)
            }
        }

        return if(headers.isEmpty()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notFoundMessage)
        } else {
            ResponseEntity(headers, HttpStatus.FOUND)
        }
    }
}