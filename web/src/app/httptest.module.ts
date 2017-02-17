import { MockBackend } from '@angular/http/testing';
import { BaseRequestOptions, Http } from '@angular/http';
import { NgModule } from '@angular/core';

@NgModule({
    providers: [
        MockBackend,
        BaseRequestOptions,
        {
            provide: Http,
            deps: [MockBackend, BaseRequestOptions],
            useFactory: (backend: MockBackend, defaultOptions: BaseRequestOptions) =>
                new Http(backend, defaultOptions)
        }],
})
export class HttpTestModule {
}
