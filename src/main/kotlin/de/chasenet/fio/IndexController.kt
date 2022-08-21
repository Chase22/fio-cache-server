package de.chasenet.fio

import com.vladsch.flexmark.ext.gfm.issues.GfmIssuesExtension
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.data.MutableDataSet
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.net.URL

@Controller
class IndexController {
    private val options = MutableDataSet().apply {
        set(
            Parser.EXTENSIONS, listOf(
                TablesExtension.create(),
                StrikethroughExtension.create(),
                GfmIssuesExtension.create()
            )
        )
        set(TablesExtension.WITH_CAPTION, false)
        set(TablesExtension.COLUMN_SPANS, false)
        set(TablesExtension.MIN_HEADER_ROWS, 1)
        set(TablesExtension.MAX_HEADER_ROWS, 1)
        set(TablesExtension.APPEND_MISSING_COLUMNS, true)
        set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
        set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)

    }
    private val parser = Parser.builder(options).build()

    private val renderer = HtmlRenderer.builder(options).build()

    @GetMapping("/", produces = [MediaType.TEXT_HTML_VALUE])
    @ResponseBody
    fun renderReadme(): String {
        return URL("https://raw.githubusercontent.com/Chase22/fio-cache-server/main/README.md").readText()
            .let(parser::parse)
            .let(renderer::render)
    }
}