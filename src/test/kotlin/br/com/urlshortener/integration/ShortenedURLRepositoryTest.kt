package br.com.urlshortener.integration

import br.com.urlshortener.model.ShortenedURLTest
import br.com.urlshortener.model.StatisticsTest
import br.com.urlshortener.repository.ShortenedURLRepository
import br.com.urlshortener.repository.StatisticsRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShortenedURLRepositoryTest {

    @Autowired
    private lateinit var shortenedURLRepository: ShortenedURLRepository
    private lateinit var statisticsRepository: StatisticsRepository
    private val url = ShortenedURLTest.build()
    private val statistic = StatisticsTest.build()

    companion object {
        @Container
        private val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:1.19.1").apply {
            withDatabaseName("testDB")
            withUsername("sz")
            withPassword("123456")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
        }
    }

    @Test
    fun `deve cadastrar uma url e inserir um registro de clique`() {  //inserir um registro de clique: adicionar uma statistc nele
        shortenedURLRepository.save(url)
        statisticsRepository.save(statistic)

        assertThat(url.statistics.size).isGreaterThan(0)
        assertThat(url.statistics).isEqualTo(statistic)
    }
}