import { MockBackend } from '@angular/http/testing';
import { XHRBackend, HttpModule } from '@angular/http';
import { NgModule } from '@angular/core';

@NgModule({
    providers: [
        MockBackend,
        {provide: XHRBackend, useFactory: (b: MockBackend) => b, deps: [MockBackend]}
    ],
    imports: [HttpModule]
})
export class HttpTestModule {
}
