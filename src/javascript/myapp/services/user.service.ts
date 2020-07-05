import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

@Injectable()
export class UserService {

    constructor(private http: HttpClient) {
    }

    register(body: any) {
        return this.http.post('api/v1/users', body);
    }
}