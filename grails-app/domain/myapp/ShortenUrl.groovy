package myapp

class ShortenUrl {

    String application = 'myapp'

    String fullUrl

    String shortUrl

    int expiration

    static constraints = {
        fullUrl maxSize: 4000, unique: 'application'
        shortUrl maxSize: 255, nullable: true
    }
}
