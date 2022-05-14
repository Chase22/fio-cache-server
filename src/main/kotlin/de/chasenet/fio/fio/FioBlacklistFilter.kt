package de.chasenet.fio.fio

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(1)
class FioBlacklistFilter(private val fioConfig: FioConfig) : Filter {
    private val antPathMatcher = AntPathMatcher()

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (request is HttpServletRequest && fioConfig.blacklist.any {
                antPathMatcher.match(it, request.servletPath)
            }) {
            (response as HttpServletResponse).apply {
                sendError(HttpStatus.BAD_REQUEST.value(), "Illegal path ${request.servletPath}")
            }
        } else {
            chain.doFilter(request, response)
        }
    }
}