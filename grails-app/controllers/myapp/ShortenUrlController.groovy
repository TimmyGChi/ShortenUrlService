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

    def retrieve() {
        log.trace "list(), params: $params"
        def res = shortenUrlService.retrieve(params)

        if (!res) {
            render(status: 404, message: 'Invalid Url!')
            return
        }
        HashMap jsonMap = new HashMap()

        jsonMap.results = [fullUrl: res.fullUrl, shortUrl: res.shortUrl, expiration: res.expiration]

        render jsonMap as JSON
    }
}
