package de.chasenet.fio.fio

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "fio")
class FioConfig {
    lateinit var url: String
    lateinit var blacklist: List<String>
}