package br.com.urlshortener.service

import br.com.urlshortener.dto.AtualizacaoStatisticsDTO
import br.com.urlshortener.dto.StatisticsView
import br.com.urlshortener.model.ShortenedURL
import br.com.urlshortener.model.Statistics
import br.com.urlshortener.repository.ShortenedURLRepository
import br.com.urlshortener.repository.StatisticsRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.stream.Collectors

@Service
class StatisticsService(private val stats: StatisticsRepository,
                        private val urls: ShortenedURLRepository
) {
    fun listarStatistics(): List<StatisticsView> {
        return stats.findAll().stream().map { StatisticsView(
            id = it.id,
            clickdateTime = it.clickdateTime,
            shortenedURLid = it.shortenedURL.id
        ) }.collect(Collectors.toList())
    }

    fun listarStatisticsById(id: Long?): List<StatisticsView> {
        val listOfStatisticsToBeListed = arrayListOf<Long?>()
        stats.findAll().forEach{
            if (it.shortenedURL.id == id) listOfStatisticsToBeListed.add(it.id)
        }

        return stats.findAllById(listOfStatisticsToBeListed).stream().map { StatisticsView(
            id = it.id,
            clickdateTime = it.clickdateTime,
            shortenedURLid = it.shortenedURL.id
        ) }.collect(Collectors.toList())
    }

//    fun contarClicks(contador: AtualizacaoStatisticsDTO) {
//        val stat = stats.findById(contador.id).get()
//        //stat.timesClicked += 1
//    }

    fun clicar(novoClick: AtualizacaoStatisticsDTO) {
        val stat = Statistics(
            shortenedURL = urls.findById(novoClick.id).orElseThrow()
        )
        stats.save(stat)
    }
}