import {Component, OnInit} from "@angular/core";

import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ShortenUrlService} from "./services/shorten-url.service";

@Component({
    selector: "application-component",
    template: require("./application.component.html"),
    styles: [String(require("./application.component.less"))]
})
export class ApplicationComponent implements OnInit {

    status: string = '';
    navigateStatus: string = '';
    shortenUrl: string = '';
    fullUrl: string = '';
    request: any;
    formGroup: FormGroup;
    navErrMsg: string = '';
    genErrMsg: string = '';
    urls: any = new Map();

    constructor(private formBuilder: FormBuilder,
                private shortenUrlService: ShortenUrlService) {
        this.shortenUrlService.navigate(null)
            .subscribe((success: any) => {
                for (let res of success.results) {
                    this.urls.set(res.fullUrl, res.shortUrl);
                }
            }, err => {
                console.log('list() error');
            });
    }

    ngOnInit() {
        this.buildFormModel();
    }

    private buildFormModel() {
        let model = {};

        model['fullUrl'] = ['', [Validators.pattern('(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?'), Validators.required]];
        model['shortUrl'] = ['', Validators.required];

        this.formGroup = this.formBuilder.group(model);
    }

    private generateUrl() {
        console.log('Generate Url');
        if (!this.isFieldValid('fullUrl')) {
            alert('Must be a valid Url!');
            return;
        }

        this.shortenUrlService.generate({fullUrl: this.getFieldValue('fullUrl')})
            .subscribe((res: any) => {
                console.log('success');
                this.updateStatus('Success')
                this.shortenUrl = res.results.shortUrl;
                this.urls.set(res.results.fullUrl, this.shortenUrl);
                this.genErrMsg = '';
            }, error => {
                console.log('error');
                this.updateStatus('Error');
                this.shortenUrl = '';
                this.genErrMsg = error.error.message;
            });
    }

    private updateStatus(status: string) {
        this.status = status;
    }

    private navigate() {
        console.log('Navigate!');

        if (!this.isFieldValid('shortUrl')) {
            alert('Url Required!');
            return;
        }

        this.shortenUrlService.navigate({shortUrl: this.getFieldValue('shortUrl')})
            .subscribe((res: any) => {
                console.log('Success');
                this.navigateStatus = 'Success';
                this.fullUrl = res.results.fullUrl
                this.request = res.results.expiration;
                this.navErrMsg = '';
            }, (error: any) => {
                console.log('Error');
                this.navigateStatus = 'Failure';
                this.navErrMsg = error.error.message;
                this.urls.delete(this.fullUrl);
                this.fullUrl = '';
                this.request = '';
            });
    }

    private isFieldValid(field: string) {
        return this.formGroup.controls[field].valid;
    }

    private getFieldValue(field: string) {
        return this.formGroup.controls[field].value;
    }
}