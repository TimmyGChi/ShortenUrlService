import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { HttpClientModule } from '@angular/common/http';

import {ApplicationComponent} from "./application.component";

import {ShortenUrlService} from "./services/shorten-url.service";
import {UserService} from "./services/user.service";

@NgModule({
    declarations: [
        ApplicationComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [
        ShortenUrlService,
        UserService
    ],
    bootstrap: [ApplicationComponent]
})
export class ApplicationModule {}