package myapp

class ShortenUrl {

    User user

    String fullUrl

    String shortUrl

    int expiration

    static constraints = {
        fullUrl maxSize: 4000, unique: 'user'
        shortUrl maxSize: 255
    }
}
