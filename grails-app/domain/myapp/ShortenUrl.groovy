package myapp

class ShortenUrl {

    String application = 'myapp'

    /** Url to be shortened. */
    String fullUrl

    /** Shortened url. */
    String shortUrl

    /** Number of time this shorten url has been requested. */
    int expiration

    static constraints = {
        fullUrl maxSize: 4000, unique: 'application'
        shortUrl maxSize: 255, nullable: true
    }
}
