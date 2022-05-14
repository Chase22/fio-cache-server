package de.chasenet.fio.fio

import de.chasenet.fio.cache.CacheEntity
import de.chasenet.fio.cache.CacheId
import de.chasenet.fio.cache.CacheRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.ZonedDateTime

@Service
class FioService(
    private val fioClient: FioClient,
    private val cacheRepository: CacheRepository
) {
    fun getValue(path: String): Mono<String> {
        return Mono
            .justOrEmpty(
                cacheRepository.getFirstById_PathAndId_TimeGreaterThanOrderById_TimeDesc(
                    path,
                    ZonedDateTime.now().minusMinutes(10)
                )
            )
            .map { it.data }
            .switchIfEmpty(getFromClient(path))
    }

    fun getAll(path: String): Flux<CacheEntity> {
        return Flux.fromIterable(cacheRepository.getAllById_Path(path))
    }

    private fun getFromClient(path: String): Mono<String> {
        return fioClient.getValue(path).publishOn(Schedulers.boundedElastic()).doOnNext {
            cacheRepository.save(CacheEntity(CacheId(path, ZonedDateTime.now()), it))
        }
    }
}