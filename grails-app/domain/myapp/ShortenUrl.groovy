package myapp

class ShortenUrl {

    String fullUrl

    String shortUrl

    static constraints = {
        fullUrl maxSize: 4000
        shortUrl maxSize: 255
    }
}
