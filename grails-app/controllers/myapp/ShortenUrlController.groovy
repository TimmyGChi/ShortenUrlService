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
        log.trace "list(), params: $params"
        if (params.containsKey('shortUrl')) {
            def res = shortenUrlService.retrieve(params)

            if (!res) {
                render(status: 404, message: 'Invalid Url!')
                return
            }
            HashMap jsonMap = new HashMap()

            jsonMap.results = [fullUrl: res.fullUrl, shortUrl: res.shortUrl, expiration: res.expiration]

            render jsonMap as JSON
        } else {
            def res = shortenUrlService.list()
            HashMap jsonMap = new HashMap()

            jsonMap.results = res.collect { it ->
                [fullUrl: it.fullUrl, shortUrl: it.shortUrl]
            }

            render jsonMap as JSON
        }

    }
}
