import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

@Injectable()
export class ShortenUrlService {

    constructor(private http: HttpClient) {
    }

    generate(body: any) {

        return this.http.post('api/v1/urls', body);
    }
}