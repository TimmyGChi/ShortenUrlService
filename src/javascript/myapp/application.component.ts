import {Component, OnInit} from "@angular/core";

import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ShortenUrlService} from "./services/shorten-url.service";

@Component({
    selector: "application-component",
    template: require("./application.component.html"),
    styles: [String(require("./application.component.less"))]
})
export class ApplicationComponent implements OnInit {

    shortenUrl: string = '';
    formGroup: FormGroup;

    constructor(private formBuilder: FormBuilder,
                private shortenUrlService: ShortenUrlService) {
    }

    ngOnInit() {
        this.buildFormModel();
    }

    private buildFormModel() {
        let model = {};

        model['fullUrl'] = ['', [Validators.pattern('(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?'), Validators.required]];

        this.formGroup = this.formBuilder.group(model);
    }

    private generateUrl() {
        console.log('Generate Url');
        if (!this.formGroup.valid) {
            alert('Must be a valid Url!');
            return;
        }

        this.shortenUrlService.generate(this.formGroup.value)
            .subscribe(success => {
                console.log('success');
            }, error => {
                console.log('error');
            });
    }
}