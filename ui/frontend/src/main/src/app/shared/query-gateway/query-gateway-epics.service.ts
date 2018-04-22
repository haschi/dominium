import { Injectable } from '@angular/core';
import { of } from 'rxjs/observable/of';
import { AppState } from '../../store/model';
import { Observable } from 'rxjs/Observable';
import { QueryAction, QueryMessageAction } from './query-gateway.model';
import {
    queryFehlgeschlagen,
    queryGelungen,
    QueryGatewayActionType
} from './query-gateway-actions.service';
import { QueryGatewayService } from './query-gateway.service';
import { createEpicMiddleware, EpicMiddleware, Epic } from 'redux-observable';

@Injectable()
export class QueryGatewayEpicsService {

  constructor(private service: QueryGatewayService) { }

    public createEpic(): [EpicMiddleware<QueryAction, AppState>] {
        return [
            createEpicMiddleware(this.createAngefordertEpic())
        ];
    }

    // queryAngefordert -> queryGelungen
    // queryAngefordert -> queryFehlgeschlagen
    private createAngefordertEpic(): Epic<QueryAction, AppState> {
        return (action$) => action$
            .ofType(QueryGatewayActionType.angefordert)
            .mergeMap(action => this.service.get(action as QueryMessageAction)
                .map(response => queryGelungen(action.message, response))
                .catch(error => this.onError(error, action)));
    }

    private onError(error: any, action: QueryAction): Observable<QueryAction> {
        return of(queryFehlgeschlagen(action.message, error.status, "Fehler"))
    }
}
