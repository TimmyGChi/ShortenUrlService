package myapp

class UrlMappings {

    static mappings = {

        "/api/v1/users" {
            controller = 'user'
            action = [GET: 'list', POST: 'post']
        }

        "/api/v1/urls" {
            controller = 'shortenUrl'
            action = [POST: 'post', GET: 'retrieve']
        }

        "/"(view:"/myapp")
        "500"(view:'/error')

    }
}
