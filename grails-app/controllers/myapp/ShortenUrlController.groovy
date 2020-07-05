package myapp

import grails.converters.JSON
import org.grails.web.servlet.mvc.GrailsWebRequest

import javax.servlet.http.HttpServletRequest

class ShortenUrlController {

    ShortenUrlService shortenUrlService

    def post() {
        HttpServletRequest requestBody = GrailsWebRequest.lookup().request
        HashMap jsonMap = new HashMap()
        def res = shortenUrlService.post(requestBody.JSON, params)

        log.trace('Post Complete')
        jsonMap.results = res

        render jsonMap as JSON
    }

    def list() {
        log.trace "list(), params: $params"
        def res = shortenUrlService.list()
        HashMap jsonMap = new HashMap()

        jsonMap.results = res.collect { r ->
            [fullUrl: r.fullUrl, shortUrl: r.shortUrl]
        }

        render jsonMap as JSON
    }
}
