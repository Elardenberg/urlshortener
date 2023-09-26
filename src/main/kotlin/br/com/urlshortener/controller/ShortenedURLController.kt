package br.com.urlshortener.controller

import br.com.urlshortener.dto.AtualizacaoStatisticsDTO
import br.com.urlshortener.dto.NovaURLDTO
import br.com.urlshortener.model.ShortenedURL
import br.com.urlshortener.model.Statistics
import br.com.urlshortener.service.ShortenedURLService
import br.com.urlshortener.service.StatisticsService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/")
class ShortenedURLController (
    private val urlService: ShortenedURLService,
    private val statisticsService: StatisticsService) {
    @GetMapping
    fun listarURL(): List<ShortenedURL> {
        return urlService.listarURL()
    }

    @GetMapping("/{text}")
    fun procurarURL(@PathVariable text: String): List<ShortenedURL> {
        return urlService.procurarURL(text)
    }

    @PostMapping
    fun cadastrarURL(@RequestBody @Valid shortenedURL: NovaURLDTO){
        urlService.cadastrarURL(shortenedURL)
    }

    @DeleteMapping("/{id}")
    fun deletarURL(@PathVariable id: Long) {
        urlService.deletar(id)
    }

    @PostMapping("/clicks")
    fun puxarStatistics() {
        statisticsService.puxarStatistics(urlService.getListOfStatistics())
    }

    @GetMapping("/clicks")
    fun listarStatistics(): List<Statistics> {
        return statisticsService.listarStatistics()
    }

    @PutMapping("/clicks")
    fun contarClicks(@RequestBody @Valid contador: AtualizacaoStatisticsDTO) {
        statisticsService.contarClicks(contador)
    }
}