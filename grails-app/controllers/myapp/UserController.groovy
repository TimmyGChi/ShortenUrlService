package myapp

import org.springframework.http.HttpStatus
import grails.converters.JSON

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
            [firstName: r.firstName, lastName: r.lastName]
        }

        render jsonMap as JSON
    }
}
