package de.chasenet.fio.cache

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime

@Repository
interface CacheRepository : JpaRepository<CacheEntity, CacheId> {
    fun getFirstById_PathAndId_TimeGreaterThanOrderById_TimeDesc(path: String, time: ZonedDateTime): CacheEntity?

    fun getAllById_Path(path: String): List<CacheEntity>
}