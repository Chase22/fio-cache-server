package de.chasenet.fio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FioCachingServerApplication

fun main(args: Array<String>) {
    runApplication<FioCachingServerApplication>(*args)
}
