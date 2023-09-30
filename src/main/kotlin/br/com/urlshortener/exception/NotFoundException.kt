package br.com.urlshortener.exception

import java.lang.RuntimeException

class NotFoundException(message: String?): RuntimeException(message) {
}