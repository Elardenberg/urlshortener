package br.com.urlshortener.service

import br.com.urlshortener.dto.AtualizacaoStatisticsDTO
import br.com.urlshortener.model.ShortenedURLTest
import br.com.urlshortener.model.ShortenedURLViewTest
import br.com.urlshortener.model.StatisticsTest
import br.com.urlshortener.model.StatisticsViewTest
import br.com.urlshortener.repository.ShortenedURLRepository
import br.com.urlshortener.repository.StatisticsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.sql.Timestamp
import java.util.*

class ShortenedURLServiceTest {
    private val url = ShortenedURLTest.build()
    private val urls = PageImpl(listOf(url))
    private val urlsList = listOf(url)
    private val statistics = listOf(StatisticsTest.build())
    private val pageable: Pageable = mockk()
    private val clickTime: Timestamp = mockk()
    private val shortenedURLView = ShortenedURLViewTest.build()
    private val statisticsView = StatisticsViewTest.build()
    private val atualizacaoStatisticsDTO: AtualizacaoStatisticsDTO = mockk()

    private val shortenedURLRepository: ShortenedURLRepository = mockk {
        every { findAll() } returns urlsList
        every { findAll(pageable) } returns urls
        every { findById(any()) } returns Optional.of(url)
    }
    private val statisticsRepository: StatisticsRepository = mockk {
        every { findAll() } returns statistics
        every { findAllById(any()) } returns statistics
        every { save(any()) } returns mockk()
    }

    private val notFoundMessage: String = "URL não encontrada!"
    private val statisticsService: StatisticsService = StatisticsService(
        statisticsRepository, shortenedURLRepository
    )
    private val request: HttpServletRequest = mockk {
        every { scheme } returns "http"
        every { serverName } returns "localhost"
        every { serverPort } returns 8080
    }

    private val shortenedURLService = ShortenedURLService(
        shortenedURLRepository, statisticsRepository, notFoundMessage, statisticsService, request
    )

    @Test
    fun `deve listar todas as estatisticas pelo id da url`() {
        statisticsService.listarStatisticsById(1L)
    }

    @Test
    fun `deve listar todas as urls`() {
        every { shortenedURLService.calcularDiferencaEmDias(clickTime, clickTime)} returns 0
        shortenedURLService.listarURL(pageable)

        verify(exactly = 1) { shortenedURLRepository.findAll(pageable) }
    }

    @Test
    fun `deve listar urls por palavra do endereco`() { // "palavra" refere-se a qualquer trecho de texto
        shortenedURLService.procurarURL("tds")
    }

    @Test
    fun `deve redirecionar o endereco para o da url cadastrada`() {
        shortenedURLService.acessarPorShortURL("tdsCo1")
    }

    @Test
    fun `deve lancar not found exception quando short url nao for achada`() {
        shortenedURLService.acessarPorShortURL("abc123")
        println(notFoundMessage)
    }
}