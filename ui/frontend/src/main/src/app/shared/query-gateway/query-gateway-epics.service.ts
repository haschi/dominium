import { Injectable } from '@angular/core';
import { of } from 'rxjs/observable/of';
import { AppState } from '../../store/model';
import { Observable } from 'rxjs/Observable';
import { QueryAction, QueryMessageAction } from './query-gateway.model';
import {
    QueryGatewayActionsService,
    QueryGatewayActionType
} from './query-gateway-actions.service';
import { QueryGatewayService } from './query-gateway.service';
import { createEpicMiddleware, EpicMiddleware, Epic } from 'redux-observable';

@Injectable()
export class QueryGatewayEpicsService {

  constructor(private aktionen: QueryGatewayActionsService,
              private service: QueryGatewayService) { }

    public createEpic(): [EpicMiddleware<QueryAction, AppState>] {
        return [
            createEpicMiddleware(this.createAngefordertEpic())
        ];
    }

    // angefordert -> gelungen
    // angefordert -> fehlgeschlagen
    private createAngefordertEpic(): Epic<QueryAction, AppState> {
        return (action$) => action$
            .ofType(QueryGatewayActionType.angefordert)
            .do(action => console.log(JSON.stringify(action)))
            .mergeMap(action => this.service.get(action as QueryMessageAction)
                .map(response => this.aktionen.gelungen(action.message, response))
                .catch(error => this.onError(error, action)));
    }

    private onError(error: any, action: QueryAction): Observable<QueryAction> {
        return of(this.aktionen.fehlgeschlagen(action.message, error.status, "Fehler"))
    }
}
