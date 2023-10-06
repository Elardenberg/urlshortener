package br.com.urlshortener.controller

import br.com.urlshortener.dto.AtualizacaoStatisticsDTO
import br.com.urlshortener.dto.NovaURLDTO
import br.com.urlshortener.dto.ShortenedURLView
import br.com.urlshortener.dto.StatisticsView
import br.com.urlshortener.model.ShortenedURL
import br.com.urlshortener.model.Statistics
import br.com.urlshortener.service.ShortenedURLService
import br.com.urlshortener.service.StatisticsService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/")
class ShortenedURLController (
    private val urlService: ShortenedURLService,
    private val statisticsService: StatisticsService) {
    @GetMapping("/")
    fun listarURL(@RequestParam(required = false) nomeURL: String?,
                  @PageableDefault(size = 5) paginacao: Pageable
    ): Iterable<ShortenedURLView> {
        return urlService.listarURL(nomeURL, paginacao)
    }

    @GetMapping("/buscar/{text}")
    fun procurarURL(@PathVariable text: String): List<ShortenedURL> {
        return urlService.procurarURL(text)
    }

    @GetMapping("/{shortURL}")
    fun redirectShortURL(@PathVariable shortURL: String): ResponseEntity<Unit> {
        return urlService.acessarPorShortURL(shortURL)
    }

    @PostMapping
    @Transactional
    fun cadastrarURL(@RequestBody @Valid shortenedURL: NovaURLDTO){
        urlService.cadastrarURL(shortenedURL)
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun deletarURL(@PathVariable id: Long) {
        urlService.deletar(id)
    }

    @PostMapping("/clicks")
    fun clicar(@RequestBody @Valid novoClick: AtualizacaoStatisticsDTO) {
        statisticsService.clicar(novoClick)
    }

    @GetMapping("/clicks")
    fun listarStatistics(): List<StatisticsView> {
        return statisticsService.listarStatistics()
    }

    @GetMapping("/clicks/id/{id}")
    fun listarStatisticsById(@PathVariable id: Long?): List<StatisticsView> {
        return statisticsService.listarStatisticsById(id)
    }

    @GetMapping("/clicks/dia/{day}")
    fun listarStatisticsByDay(@PathVariable day: String): List<StatisticsView> {
        return statisticsService.listarStatisticsByDay(day)
    }

//    @PutMapping("/clicks")
//    @Transactional
//    fun contarClicks(@RequestBody @Valid contador: AtualizacaoStatisticsDTO) {
//        statisticsService.contarClicks(contador)
//    }
}