package myapp

import grails.gorm.transactions.Transactional

@Transactional
class UserService {

    def list() {
        User.list()
    }

    def post(content, params) {
        log.trace("post(): $params")

        def instance = User.findByUsername(content['username'])

        if (!instance) {
            instance = new User(username: content['username']).save()
        }

        instance
    }
}
