package myapp

import grails.gorm.transactions.Transactional

@Transactional
class UserService {

    def list() {
        User.list()
    }
}
