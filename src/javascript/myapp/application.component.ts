import {Component, OnInit} from "@angular/core";

import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ShortenUrlService} from "./services/shorten-url.service";
import {UserService} from "./services/user.service";

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
    currentUser: string;
    errorMsg: string = '';

    constructor(private formBuilder: FormBuilder,
                private shortenUrlService: ShortenUrlService,
                private userService: UserService) {
    }

    ngOnInit() {
        this.buildFormModel();
    }

    private buildFormModel() {
        let model = {};

        model['fullUrl'] = ['', [Validators.pattern('(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?'), Validators.required]];
        model['user'] = ['', Validators.required];
        model['shortUrl'] = ['', Validators.required];

        this.formGroup = this.formBuilder.group(model);
    }

    private generateUrl() {
        console.log('Generate Url');
        if (!this.isFieldValid('fullUrl')) {
            alert('Must be a valid Url!');
            return;
        }

        if (!this.currentUser) {
            alert('Please first login or register user!');
            return;
        }

        this.shortenUrlService.generate({fullUrl: this.getFieldValue('fullUrl'), username: this.currentUser})
            .subscribe((res: any) => {
                console.log('success');
                this.updateStatus('Success')
                this.shortenUrl = res.results.shortUrl;
            }, error => {
                console.log('error');
                this.updateStatus('Error');
                this.shortenUrl = '';
                this.errorMsg = error.message;
            });
    }

    private updateStatus(status: string) {
        this.status = status;
    }

    private registerUser() {
        console.log('Login/Register User');

        if (!this.isFieldValid('user')) {
            alert('User is required!');
            return;
        }
        this.userService.register({username: this.getFieldValue('user')})
            .subscribe((res: any) => {
                this.currentUser = res.results.username;
            }, error => {

            });
    }

    private navigate() {
        console.log('Navigate!');

        if (!this.isFieldValid('shortUrl')) {
            alert('Url Required!');
            return;
        }

        if (!this.currentUser) {
            alert('Please first login or register user!');
            return;
        }
        this.shortenUrlService.navigate({shortUrl: this.getFieldValue('shortUrl'), username: this.currentUser})
            .subscribe((res: any) => {
                console.log('Success');
                this.navigateStatus = 'Success';
                this.fullUrl = res.results.fullUrl
                this.request = res.results.expiration;
                this.errorMsg = '';
            }, (error: any) => {
                console.log('Error');
                this.navigateStatus = 'Failure';
                this.errorMsg = error.error.message;
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