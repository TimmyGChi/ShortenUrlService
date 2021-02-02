package timapp

class UrlMappings {

    static mappings = {

        "/api/v1/urls" {
            controller = 'shortenUrl'
            action = [POST: 'post', GET: 'retrieve']
        }

        "/"(view:"/myapp")
        "500"(view:'/error')

    }
}
