package br.com.urlshortener.model

import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

data class Statistics (
    @field:NotNull val id: Long,
    @field:NotNull val shortURLid: Long,
    @field:NotNull var timesClicked: Long,
    val clickdateTime: LocalDateTime = LocalDateTime.now()
)
