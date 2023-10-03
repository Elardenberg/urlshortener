package br.com.urlshortener.repository

import br.com.urlshortener.model.ShortenedURL
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ShortenedURLRepository: JpaRepository<ShortenedURL, Long> {
    fun findByFullURL(nomeURL: String,
                      paginacao: Pageable): Page<ShortenedURL>
}