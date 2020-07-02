import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { HttpClientModule } from '@angular/common/http';

import {ApplicationComponent} from "./application.component";

import {ShortenUrlService} from "./services/shorten-url.service";

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
        ShortenUrlService
    ],
    bootstrap: [ApplicationComponent]
})
export class ApplicationModule {}