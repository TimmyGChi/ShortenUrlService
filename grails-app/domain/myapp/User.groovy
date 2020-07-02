package myapp

class User {

    /** First name of the user. */
    String firstName
    /** Last name of the user. */
    String lastName

    static constraints = {
        firstName nullable: true, maxSize: 100
        lastName nullable: true, maxSize: 100
    }
}
