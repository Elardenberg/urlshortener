package br.com.urlshortener.controller

import br.com.urlshortener.dto.NovaURLDTO
import br.com.urlshortener.dto.ShortenedURLView
import br.com.urlshortener.dto.StatisticsView
import br.com.urlshortener.service.ShortenedURLService
import br.com.urlshortener.service.StatisticsService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class ShortenedURLController (
    private val urlService: ShortenedURLService,
    private val statisticsService: StatisticsService) {
    @GetMapping
    fun listarURL(@PageableDefault(size = 5) paginacao: Pageable
    ): Iterable<ShortenedURLView> {
        return urlService.listarURL(paginacao)
    }

    @GetMapping("/buscar/{text}")
    fun procurarURL(@PathVariable text: String): List<ShortenedURLView> {
        return urlService.procurarURL(text)
    }

    @GetMapping("/{shortURL}")
    fun redirecionarShortURL(@PathVariable shortURL: String): ResponseEntity<String> {
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

//    @PostMapping("/clicks")
//    fun clicar(@RequestBody @Valid novoClick: AtualizacaoStatisticsDTO) {
//        statisticsService.clicar(novoClick)
//    }

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