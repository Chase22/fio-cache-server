package de.chasenet.fio.fio

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties(prefix="fio")
class FioConfig(
    val blacklist: List<String>
)