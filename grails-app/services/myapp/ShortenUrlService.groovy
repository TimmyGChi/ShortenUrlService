package myapp

import grails.gorm.transactions.Transactional

import java.security.MessageDigest

@Transactional
class ShortenUrlService {

    static int RADIX = 16
    static int HASH_LENGTH = 7
    static String MD_ALGORITHM = 'MD5'
    static String PREFIX_URL = "https://bit.ly/"
    static int EXPIRED = 10

    def post(content, params) {
        log.trace("post(): $params")

        User user = User.findByUsername(content['username'])
        def instance = ShortenUrl.findByUserAndFullUrl(user, content['fullUrl'])
        if (!instance) {
            String shortUrl = generateShortUrl(content['fullUrl'])

            instance = new ShortenUrl(user: user, fullUrl: content['fullUrl'], shortUrl: shortUrl).save(flush: true)
        }
        instance
    }

    def retrieve(params) {
        log.trace("retrieve(): $params")
        String url = params['shortUrl']
        User user = User.findByUsername(params['username'] as String)

        def instance = ShortenUrl.findByUserAndShortUrl(user, url)

        if (!instance) {
            return null
        }
        if (isUrlInvalid(instance)) {
            instance.delete()
            return null
        }
        instance.expiration++
        instance
    }

    String generateShortUrl(String url) {
        MessageDigest md = MessageDigest.getInstance(MD_ALGORITHM)
        byte[] md5Hash = md.digest(url.bytes)

        "$PREFIX_URL${new BigInteger(1, md5Hash).toString(RADIX).substring(0, HASH_LENGTH)}"
    }

    boolean isUrlInvalid(instance) {
        instance.expiration == EXPIRED
    }
}
