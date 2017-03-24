import { Request, RequestMethod } from '@angular/http';
import { ContentType } from '@angular/http/src/enums';


// see https://blog.thoughtram.io/angular/2016/12/27/angular-2-advance-testing-with-custom-matchers.html

declare var global: any;
const _global = <any>(typeof window === 'undefined' ? global : window);

/**
 * Extend the API to support chaining with custom matchers
 */
export const expect: (actual: any) => NgMatchers = <any> _global.expect;

/**
 * Jasmine matchers that support checking custom CSS conditions.
 * !! important to add your custom matcher to the interface
 */
export interface NgMatchers extends jasmine.Matchers {
    not: NgMatchers;
    toPostJson(expected: HttpPostExpectation): boolean;
}

interface HttpPostExpectation {
    url: string;
    body: any;
}

const httpMatcher: jasmine.CustomMatcherFactories = {
    toPostJson: (util: jasmine.MatchersUtil) => {
        return {
            compare: function (
                actual: Request, expected: HttpPostExpectation): jasmine.CustomMatcherResult {
                if (actual.method !== RequestMethod.Post) {
                    return {pass: false, message: 'Expected POST Request'};
                }

                if (actual.detectContentType() !== ContentType.JSON) {
                    return {pass: false, message: 'Expected Content Type JSON'};
                }

                if (actual.url !== expected.url) {
                    return {
                        pass: false,
                        message: 'Expected URL ' + actual.url + ' to equal ' + expected.url
                    };
                }

                if (!util.equals(actual.json(), expected.body)) {
                    return {
                        pass: false,
                        message: 'Expected ' + JSON.stringify(actual.json())
                            + ' to equal ' + JSON.stringify(expected.body)
                    };
                }
                return {pass: true, message: ''};
            }
        };
    }
};

export { httpMatcher, HttpPostExpectation }
