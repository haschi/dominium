import { Injectable } from '@angular/core';
import { dispatch, select } from '@angular-redux/store';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { QueryGatewayActionsService } from './query-gateway-actions.service';
import { QueryType } from './query-type';
import { QueryMessage, QueryMessageAction, QueryResponse } from './query-gateway.model';
import { LoggerService } from '../logger.service';

import 'rxjs/add/operator/retryWhen';
import 'rxjs/add/operator/delay';
import 'rxjs/add/operator/timeout';
import 'rxjs/add/operator/take';
import 'rxjs/add/operator/concat';
import 'rxjs/add/observable/timer';
import 'rxjs/add/observable/throw';

@Injectable()
export class QueryGatewayService {

    constructor(
        private http: HttpClient,
        private aktionen: QueryGatewayActionsService,
        private logger: LoggerService) {
    }

    @select(['query', 'sendet'])
    sendet$: Observable<boolean>;

    @select(['query', 'message'])
    message$: Observable<QueryMessage>;

    @select(['query', 'response'])
    status$: Observable<QueryResponse>;

    get(action: QueryMessageAction): Observable<HttpResponse<Object>> {
        this.logger.log(`QUERY get ${action.message.type}`);
        return this.http.post("/gateway/query", action.message, {observe: 'response'})
            .retryWhen(error =>
                error.do(val => this.logger.log(`QUERY get ERROR ${val.message}`))
                    .delay(1000)
                    .take(4)
                    .concat(Observable.throw(new Error('Wiederholungslimit überschritten!'))));
    }

    @dispatch()
    send(type: string, payload: any, meta: any): QueryMessageAction {
        this.logger.log(`QUERY send ${type}`);
        return this.aktionen.angefordert(type, payload, meta)
    }
}
