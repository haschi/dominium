"use strict";
var testing_1 = require('@angular/core/testing');
// Load the implementations that should be tested
var app_component_1 = require('./app.component');
var app_service_1 = require('./app.service');
describe('App', function () {
    // provide our implementations or mocks to the dependency injector
    beforeEach(function () { return testing_1.TestBed.configureTestingModule({
        providers: [
            app_service_1.AppState,
            app_component_1.AppComponent
        ] }); });
    it('should have a url', testing_1.inject([app_component_1.AppComponent], function (app) {
        expect(app.url).toEqual('https://twitter.com/AngularClass');
    }));
});
//# sourceMappingURL=app.component.spec.js.map