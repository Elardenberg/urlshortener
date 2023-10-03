package br.com.urlshortener.model

import jakarta.persistence.*
import org.hibernate.engine.internal.Cascade
import org.jetbrains.annotations.NotNull
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity
data class Statistics (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val clickdateTime: Timestamp = Timestamp(System.currentTimeMillis()),
    @ManyToOne
    @JoinColumn(name="shortenedurl_id")
    val shortenedURL: ShortenedURL
)
