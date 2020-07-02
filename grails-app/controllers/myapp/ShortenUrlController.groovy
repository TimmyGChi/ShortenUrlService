package myapp

import org.grails.web.servlet.mvc.GrailsWebRequest

import javax.servlet.http.HttpServletRequest

class ShortenUrlController {

    ShortenUrlService shortenUrlService

    def post() {
        HttpServletRequest requestBody = GrailsWebRequest.lookup().request

        def res = shortenUrlService.post(requestBody.JSON, params)

        log.trace('Post Complete')
        render res
        true
    }
}
