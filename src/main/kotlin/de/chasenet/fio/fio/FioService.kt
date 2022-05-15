package de.chasenet.fio.fio

import de.chasenet.fio.cache.CacheEntity
import de.chasenet.fio.cache.CacheId
import de.chasenet.fio.cache.CacheRepository
import de.chasenet.fio.cache.CacheResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.time.ZonedDateTime

@Service
class FioService(
    private val fioClient: FioClient,
    private val cacheRepository: CacheRepository
) {
    fun getValue(path: String, maxAge: Duration): Mono<CacheResponseEntity> {
        return Mono.justOrEmpty(
            cacheRepository.getFirstById_PathAndId_TimeGreaterThanOrderById_TimeDesc(
                path,
                ZonedDateTime.now().minus(maxAge)
            )
        ).map { CacheResponseEntity(it, true) }
            .switchIfEmpty(getFromClient(path))
    }

    fun getAll(path: String): Flux<CacheEntity> {
        return Flux.fromIterable(cacheRepository.getAllById_Path(path))
    }

    private fun getFromClient(path: String): Mono<CacheResponseEntity> {
        return fioClient.getValue(path).publishOn(Schedulers.boundedElastic()).map { data ->
            CacheResponseEntity(cacheRepository.save(CacheEntity(CacheId(path, ZonedDateTime.now()), data)), false)
        }
    }

}