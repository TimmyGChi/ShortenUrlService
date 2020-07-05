package myapp

import grails.gorm.transactions.Transactional

import java.security.MessageDigest

@Transactional
class ShortenUrlService {

    def post(content, params) {
        log.trace("post(): $params")

        String shortUrl = generateShortUrl(content['fullUrl'])
        def instance = new ShortenUrl(user: User.findByUsername(content['username']), fullUrl: content['fullUrl'], shortUrl: shortUrl).save(flush: true)

        if (!instance) {
            throw new RuntimeException("Short Url already generated for ${content['fullUrl']}")
        }
        instance
    }

    def list() {
        ShortenUrl.list()
    }

    String generateShortUrl(String url) {
        MessageDigest md = MessageDigest.getInstance("MD5")
        byte[] md5Hash = md.digest(url.bytes)

        "https://bit.ly/${new BigInteger(1, md5Hash).toString(16).substring(0, 7)}"
    }
}
