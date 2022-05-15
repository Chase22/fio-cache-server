package de.chasenet.fio.cache

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMapAdapter
import java.time.ZoneOffset
import java.time.ZonedDateTime

data class CacheResponseEntity(
    val fromCache: Boolean,
    val time: ZonedDateTime,
    val data: String
) {
    constructor(entity: CacheEntity, fromCache: Boolean) : this(
        fromCache,
        entity.id.time.withZoneSameInstant(ZoneOffset.UTC),
        entity.data
    )

    fun toResponseEntity(): ResponseEntity<String> = ResponseEntity(
        data, MultiValueMapAdapter(
            mapOf(
                "X-Fio-Cache-Hit" to listOf(fromCache.toString()),
                "X-Fio-Cache-Age" to listOf(time.toString())
            )
        ), HttpStatus.OK
    )
}