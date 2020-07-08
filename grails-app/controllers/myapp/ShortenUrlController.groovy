package myapp

import grails.converters.JSON
import org.grails.web.servlet.mvc.GrailsWebRequest

class ShortenUrlController {

    ShortenUrlService shortenUrlService

    def post() {
        def content = GrailsWebRequest.lookup().request.JSON
        HashMap jsonMap = new HashMap()
        def res = shortenUrlService.post(content, params)

        if (!res) {
            render(status: 422, message: "Shorten Url already exists for ${content}")
        }
        log.trace('Post Complete')
        jsonMap.results = res

        render jsonMap as JSON
    }

    def retrieve() {
        HashMap jsonMap = new HashMap()

        if (params.containsKey('shortUrl')) {
            log.trace "retrieve(), params: $params"
            def res = shortenUrlService.retrieve(params)

            if (!res) {
                render(status: 404, message: 'Invalid Url!')
                return
            }

            jsonMap.results = [id: res.id, fullUrl: res.fullUrl, shortUrl: res.shortUrl, expiration: res.expiration]
        } else {
            log.trace "list(), params: $params"
            def res = shortenUrlService.list()

            jsonMap.results = res.collect { it ->
                [id: it.id, fullUrl: it.fullUrl, shortUrl: it.shortUrl, expiration: it.expiration]
            }
        }
        render jsonMap as JSON
    }
}
