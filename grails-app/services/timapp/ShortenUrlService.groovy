package timapp

import grails.gorm.transactions.Transactional

@Transactional
class ShortenUrlService {

    static final char[] converter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray()
    static String PREFIX_URL = "https://short.en/"
    static int EXPIRED = 10
    static int RADIX = 62

    def post(content, params) {
        log.trace("post(): $params")

        def instance = ShortenUrl.findByFullUrl(content['fullUrl'])
        if (instance) {
            // Unique Constraint
            return null
        }
        instance = new ShortenUrl(fullUrl: content['fullUrl']).save(flush: true)
        String suffix = getBase62From10(instance.id)

        instance.shortUrl = "$PREFIX_URL$suffix"
        instance
    }

    def list() {
        ShortenUrl.list()
    }

    def retrieve(params) {
        log.trace("retrieve(): $params")
        String url = params['shortUrl']

        def instance = ShortenUrl.findByShortUrl(url)

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

    String getBase62From10(long uniqueId) {
        String number = String.valueOf(uniqueId)
        BigInteger bigInteger = new BigInteger(number)
        BigInteger base62 = BigInteger.valueOf(RADIX)
        char[] arr = new char[number.length()]
        int position = number.length() - 1

        while (bigInteger.compareTo(base62) >= 0) {
            int mod = bigInteger.mod(base62).intValue()

            arr[position--] = converter[mod]
            bigInteger = bigInteger.divide(base62)
        }
        arr[position] = converter[bigInteger.intValue()]

        new String(arr, position, (number.length() - position))
    }

    boolean isUrlInvalid(instance) {
        instance.expiration == EXPIRED
    }
}
