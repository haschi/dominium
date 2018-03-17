import { ReplaySubject } from 'rxjs/ReplaySubject';
import { ActivatedRouteSnapshot, convertToParamMap, ParamMap, Params } from '@angular/router';

/**
 * An ActivateRoute test double with a `paramMap` observable.
 * Use the `setParamMap()` method to add the next `paramMap` value.
 */
export class ActivatedRouteStub {
    // Use a ReplaySubject to share previous values with subscribers
    // and pump new values into the `paramMap` observable
    private subject = new ReplaySubject<ParamMap>();
    private params: Params;

    constructor(initialParams?: Params) {
        this.params = initialParams;
        this.setParamMap(initialParams);
    }

    /** The mock paramMap observable */
    readonly paramMap = this.subject.asObservable();

    /** Set the paramMap observables's next value */
    setParamMap(params?: Params) {
        this.params = params;
        this.subject.next(convertToParamMap(params));
    };

    get snapshot(): ActivatedRouteSnapshot
    {
        return {
            params: this.params,
            url: [],
            queryParams: null,
            fragment: '',
            data: null,
            outlet: null,
            component: null,
            routeConfig: null,
            root: null,
            parent: null,
            firstChild: null,
            children: null,
            pathFromRoot: null,
            paramMap: null,
            queryParamMap: null
        }
    }
}