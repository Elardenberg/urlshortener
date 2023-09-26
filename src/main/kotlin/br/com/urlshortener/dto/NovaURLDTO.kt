package br.com.urlshortener.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class NovaURLDTO (
    @field:NotEmpty @field:Size(min = 15, max = 200) val fullURL: String
)
