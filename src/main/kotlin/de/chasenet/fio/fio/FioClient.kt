package de.chasenet.fio.fio

import org.springframework.boot.info.BuildProperties
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@Service
class FioClient(fioConfig: FioConfig, buildProperties: BuildProperties) {
    var client = WebClient.builder()
        .baseUrl(fioConfig.url)
        .defaultHeader(HttpHeaders.USER_AGENT, "${buildProperties.artifact}:${buildProperties.version}")
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .build()

    fun getValue(path: String): Mono<String> {
        return client.get().uri(path).exchangeToMono {
            if (it.statusCode() == HttpStatus.OK) {
                it.bodyToMono(String::class.java)
            } else {
                Mono.error(Exception("Unexpected status code ${it.statusCode()} while fetching data from $path"))
            }
        }
    }
}