package myapp

import grails.gorm.transactions.Transactional

@Transactional
class ShortenUrlService {

    def post(content, params) {
        log.trace("post(): $params")

//        def instance = ShortenUrl.create(content)

        def instance = new ShortenUrl(fullUrl: content['fullUrl'], shortUrl: 'dummy short').save(flush: true)

        instance
    }
}
