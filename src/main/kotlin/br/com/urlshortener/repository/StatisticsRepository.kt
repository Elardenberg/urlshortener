package br.com.urlshortener.repository

import br.com.urlshortener.model.Statistics
import org.springframework.data.jpa.repository.JpaRepository

interface StatisticsRepository: JpaRepository<Statistics, Long> {
}