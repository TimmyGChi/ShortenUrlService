package myapp

import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.http.HttpStatus
import grails.converters.JSON

import javax.servlet.http.HttpServletRequest

class UserController {

    UserService userService

    def noMatchingMethodFound() {
        respond((Object) null, [status: HttpStatus.METHOD_NOT_ALLOWED])
    }

    def list() {
        log.trace "list(), params: $params"
        def res = userService.list()
        HashMap jsonMap = new HashMap()

        jsonMap.results = res.collect { r ->
            [username: r.username]
        }

        render jsonMap as JSON
    }

    def post() {
        HttpServletRequest requestBody = GrailsWebRequest.lookup().request
        HashMap jsonMap = new HashMap()

        def res = userService.post(requestBody.JSON, params)

        jsonMap.results = res

        render jsonMap as JSON
    }
}
