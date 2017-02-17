import {Request, RequestMethod} from "@angular/http";
import {ContentType} from "@angular/http/src/enums";

interface HttpPostExpectation {
    url: string;
    body: any;
}

const httpMatcher: jasmine.CustomMatcherFactories = {
    toPostJson: (util: jasmine.MatchersUtil) => {
        return {
            compare: function (actual: Request, expected: HttpPostExpectation): jasmine.CustomMatcherResult {
                if (actual.method != RequestMethod.Post) {
                    return {pass: false, message: "Expected POST Request"}
                }

                if (actual.detectContentType() != ContentType.JSON) {
                    return {pass: false, message: "Expected Content Type JSON"}
                }

                if (actual.url != expected.url) {
                    return {pass: false, message: "Expected URL " + actual.url + " to equal " + expected.url}
                }

                if (!util.equals(actual.json(), expected.body)) {
                    return {
                        pass: false,
                        message: "Expected " + JSON.stringify(actual.json()) + " to equal " + JSON.stringify(expected.body)
                    }
                }
                return {pass: true, message: ""}
            }
        }
    }
};

export {httpMatcher, HttpPostExpectation}
