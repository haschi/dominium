import { Injectable } from '@angular/core';
import { dispatch, select } from '@angular-redux/store';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { QueryGatewayActionsService } from './query-gateway-actions.service';
import { QueryType } from './query-type';
import { QueryMessage, QueryMessageAction, QueryResponse } from './query-gateway.model';

@Injectable()
export class QueryGatewayService {

    constructor(private http: HttpClient, private aktionen: QueryGatewayActionsService) {
    }

    @select(['query', 'sendet'])
    sendet$: Observable<boolean>;

    @select(['query', 'message'])
    message$: Observable<QueryMessage>;

    @select(['query', 'response'])
    status$: Observable<QueryResponse>;

    get(action: QueryMessageAction): Observable<HttpResponse<Object>> {
        return this.http.post("/gateway/query", action.message, {observe: 'response'});
    }

    @dispatch()
    send(type: QueryType, payload: any, meta: any): QueryMessageAction {
        return this.aktionen.angefordert(type, payload, meta)
    }
}
