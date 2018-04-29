import { Injectable } from '@angular/core';
import { dispatch, select } from '@angular-redux/store';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { LoggerService } from '../logger.service';

import 'rxjs/add/operator/retryWhen';
import 'rxjs/add/operator/delay';
import 'rxjs/add/operator/timeout';
import 'rxjs/add/operator/take';
import 'rxjs/add/operator/concat';
import 'rxjs/add/observable/timer';
import 'rxjs/add/observable/throw';
import {
    queryAngefordert,
    QueryMessage,
    QueryMessageAction,
    QueryResponse
} from './query.redux';

@Injectable()
export class QueryGatewayService {

    constructor(
        private http: HttpClient,
        private logger: LoggerService) {
    }

    @select(['query', 'sendet'])
    sendet$: Observable<boolean>;

    @select(['query', 'message'])
    message$: Observable<QueryMessage>;

    @select(['query', 'response'])
    status$: Observable<QueryResponse>;

    @dispatch()
    send(type: string, payload: any, result: string): QueryMessageAction {
        return queryAngefordert(type, payload, result)
    }
}
