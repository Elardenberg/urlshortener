package br.com.urlshortener.model

import jakarta.persistence.*

@Entity
data class ShortenedURL (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val fullURL: String,
    val shortURL: String,
    @OneToMany(mappedBy = "shortenedURL", fetch = FetchType.EAGER)
    val statistics: List<Statistics> = ArrayList()
)