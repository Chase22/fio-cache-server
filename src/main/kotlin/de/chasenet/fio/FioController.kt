package de.chasenet.fio

import de.chasenet.fio.cache.CacheResponseEntity
import de.chasenet.fio.fio.FioService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.ZonedDateTime

@RestController
class FioController(
    private val fioService: FioService,
) {
    @GetMapping("/{*path}")
    fun getPath(
        @PathVariable path: String,
        @RequestParam("maxAge", required = false, defaultValue = "PT10M") maxAge: Duration
    ): Mono<ResponseEntity<String>> {
        return fioService.getValue(path, maxAge).map(CacheResponseEntity::toResponseEntity)
    }

    @GetMapping("/history/{*path}")
    fun getHistoricalData(@PathVariable path: String): Flux<HistoryEntity> {
        return fioService.getAll(path).map { HistoryEntity(it.id.time, it.data) }
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(FioController::class.java)
    }

    data class HistoryEntity(
        val dateTime: ZonedDateTime,
        val data: String
    )
}