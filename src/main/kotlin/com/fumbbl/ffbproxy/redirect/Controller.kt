package com.fumbbl.ffbproxy.redirect

import com.fumbbl.ffbproxy.config.Connections
import com.fumbbl.ffbproxy.resolve.UrlRewriter
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.view.RedirectView
import java.net.URL
import javax.servlet.http.HttpServletRequest

@Controller
class Controller(val rewriter: UrlRewriter, val connections: Connections) {

    @RequestMapping("/public/jnlp")
    fun jnlp(request: HttpServletRequest): RedirectView {
        return RedirectView(rewriter.buildJnlpUrl(requestUrl(request), connections.getPrimary(), "/public/jnlp").toString())
    }

    private fun requestUrl(request: HttpServletRequest): URL {
        var urlString = request.requestURL.toString()
        if (request.queryString.isNotBlank()) {
            urlString += "?" + request.queryString
        }
        return URL(urlString)

    }

    @RequestMapping("/ffb/**")
    fun ffb(request: HttpServletRequest): RedirectView {
        return RedirectView(rewriter.buildFfbUrl(requestUrl(request), connections.getPrimary(), "/ffb").toString())
    }
}
