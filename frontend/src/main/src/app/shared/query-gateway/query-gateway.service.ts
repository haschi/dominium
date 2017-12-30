import { Injectable } from '@angular/core';
import { dispatch, select } from '@angular-redux/store';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { QueryGatewayActionsService } from './query-gateway-actions.service';
import { QueryType } from './query-type';
import { QueryMessage, QueryMessageAction, QueryResponse } from './query-gateway.model';
import { LoggerService } from '../logger.service';

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
        return this.http.post("/gateway/query", action.message, {observe: 'response'});
    }

    @dispatch()
    send(type: string, payload: any, meta: any): QueryMessageAction {
        this.logger.log(`QUERY send ${type}`);
        return this.aktionen.angefordert(type, payload, meta)
    }
}
