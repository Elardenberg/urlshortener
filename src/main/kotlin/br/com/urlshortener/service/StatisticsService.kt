package br.com.urlshortener.service

import br.com.urlshortener.dto.AtualizacaoStatisticsDTO
import br.com.urlshortener.dto.StatisticsView
import br.com.urlshortener.model.Statistics
import br.com.urlshortener.repository.ShortenedURLRepository
import br.com.urlshortener.repository.StatisticsRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

@Service
class StatisticsService(private val stats: StatisticsRepository,
                        private val urls: ShortenedURLRepository
) {
    fun listarStatistics(): List<StatisticsView> {
        return mapView(stats.findAll())
    }

    fun listarStatisticsById(id: Long?): List<StatisticsView> {
        val listOfStatisticsToBeListed = arrayListOf<Long?>()
        stats.findAll().forEach{
            if (it.shortenedURL.id == id) listOfStatisticsToBeListed.add(it.id)
        }

        return mapView(stats.findAllById(listOfStatisticsToBeListed))
    }

    fun listarStatisticsByDay(day: String): List<StatisticsView> {
        val listOfStatisticsToBeListed = arrayListOf<Long?>()
        val searchedDay = stringToTimestamp(day)
        stats.findAll().forEach{
            if (haveSameDay(searchedDay, it.clickdateTime)) listOfStatisticsToBeListed.add(it.id)
        }

        return mapView(stats.findAllById(listOfStatisticsToBeListed))
    }

    fun stringToTimestamp(dateTimeStr: String, format: String = "dd-MM-yyyy"): Timestamp? {
        return try {
            val dateFormat = SimpleDateFormat(format)
            val parsedDate: Date = dateFormat.parse(dateTimeStr)
            Timestamp(parsedDate.time)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun haveSameDay(timestamp1: Timestamp?, timestamp2: Timestamp): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = timestamp1
        val cal2 = Calendar.getInstance()
        cal2.time = timestamp2

        val year1 = cal1.get(Calendar.YEAR)
        val month1 = cal1.get(Calendar.MONTH)
        val day1 = cal1.get(Calendar.DAY_OF_MONTH)

        val year2 = cal2.get(Calendar.YEAR)
        val month2 = cal2.get(Calendar.MONTH)
        val day2 = cal2.get(Calendar.DAY_OF_MONTH)

        return year1 == year2 && month1 == month2 && day1 == day2
    }

    fun clicar(novoClick: AtualizacaoStatisticsDTO) {
        val stat = Statistics(
            shortenedURL = urls.findById(novoClick.id).orElseThrow()
        )
        stats.save(stat)
    }

    fun mapView(l: MutableList<Statistics>): ArrayList<StatisticsView> {
        return ArrayList(l.stream().map { StatisticsView(
            id = it.id,
            shortenedURLid = it.shortenedURL.id,
            fullURL = it.shortenedURL.fullURL,
            clickdateTime = it.clickdateTime
        ) }.collect(Collectors.toList()))
    }
}