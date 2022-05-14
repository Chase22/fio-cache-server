package de.chasenet.fio.cache

import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import java.io.Serializable
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "cache")
data class CacheEntity(
    @EmbeddedId val id: CacheId,

    @Column(name = "data")
    val data: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CacheEntity

        return id == other.id
    }

    override fun hashCode(): Int = Objects.hash(id)

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(EmbeddedId = $id , data = $data )"
    }
}

@Embeddable
data class CacheId(
    @Column(name = "path") val path: String,
    @Column(name = "timestamp") val time: ZonedDateTime
) : Serializable {
    companion object {
        private const val serialVersionUID = 4742354570070221814L
    }
}