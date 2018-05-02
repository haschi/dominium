import { NgRedux, select } from '@angular-redux/store';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { QueryGatewayService } from '../../shared/query-gateway/query-gateway.service';
import { QueryType } from '../shared/query-type';
import { ResultType } from '../shared/result-type';
import { AppState } from '../../store/model';
import { Eroeffnungsbilanz } from './bilanz.model';

@Injectable()
export class BilanzService {

    @select(['inventur', 'eroeffnungsbilanz'])
    bilanz$: Observable<Eroeffnungsbilanz>;

    constructor(private query: QueryGatewayService, private store: NgRedux<AppState>) {
    }

    ladeBilanz() {
        let id = this.store.getState().inventur.inventurId;

        this.query.send(
            QueryType.LeseEroeffnungsbilanz,
            {inventurId: id},
            ResultType.Eroeffnungsbilanz);
    }
}
