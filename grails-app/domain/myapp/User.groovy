package myapp

class User {

    String username

    static constraints = {
        username maxSize: 100
    }
}
