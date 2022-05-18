package de.chasenet.fio

import de.chasenet.fio.fio.FioConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(FioConfig::class)
@SpringBootApplication
class FioCachingServerApplication

fun main(args: Array<String>) {
    runApplication<FioCachingServerApplication>(*args)
}
