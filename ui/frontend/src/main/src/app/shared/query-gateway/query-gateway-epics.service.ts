import { Injectable } from '@angular/core';
import { of } from 'rxjs/observable/of';
import { AppState } from '../../store/model';
import { Observable } from 'rxjs/Observable';
import { QueryGatewayService } from './query-gateway.service';
import { createEpicMiddleware, EpicMiddleware, Epic } from 'redux-observable';
import {
    createAngefordertEpic,
    QueryAction,
    queryFehlgeschlagen,
    queryGelungen,
    QueryMessageAction
} from './query-reducers';

@Injectable()
export class QueryGatewayEpicsService {

  constructor(private service: QueryGatewayService) { }

    public createEpic(): [EpicMiddleware<QueryAction, AppState>] {
        return [
            createEpicMiddleware(createAngefordertEpic(this.service))
        ];
    }
}
