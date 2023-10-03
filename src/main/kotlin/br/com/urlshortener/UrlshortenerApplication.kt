package br.com.urlshortener

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
class UrlshortenerApplication
fun main(args: Array<String>) {
	runApplication<UrlshortenerApplication>(*args)
}
