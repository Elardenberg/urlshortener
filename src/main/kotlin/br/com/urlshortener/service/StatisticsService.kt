package br.com.urlshortener.service

import br.com.urlshortener.dto.AtualizacaoStatisticsDTO
import br.com.urlshortener.model.ShortenedURL
import br.com.urlshortener.model.Statistics
import org.springframework.stereotype.Service

@Service
class StatisticsService(private var stats: List<Statistics> = ArrayList()) {
    fun puxarStatistics(listOfStatistics: List<Statistics>){
        stats = listOfStatistics
    }

    fun listarStatistics(): List<Statistics> {
        return stats
    }
    fun contarClicks(contador: AtualizacaoStatisticsDTO) {
        val stat = stats.stream().filter {
            it.id == contador.id
        }.findFirst().get()
        stat.timesClicked += 1
    }
}