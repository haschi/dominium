import { Component, OnInit } from '@angular/core';
import { Inventar } from '../inventar';
import { ActivatedRoute, Router } from '@angular/router';
import { QueryGatewayService } from '../../shared/query-gateway/query-gateway.service';
import { QueryType } from '../../shared/query-gateway/query-type';
import { Observable } from 'rxjs/Observable';

import { select } from '@angular-redux/store';
import { LoggerService } from '../../shared/logger.service';
import { ResultType } from '../../shared/query-gateway/result-type';
import { InventurService } from '../inventur.service';

@Component({
    selector: 'app-inventar',
    templateUrl: './inventar.component.html',
    styleUrls: ['./inventar.component.scss']
})
export class InventarComponent implements OnInit {

    @select(['inventur', 'inventar'])
    inventar$: Observable<Inventar>;

    constructor(private active: ActivatedRoute,
                private query: QueryGatewayService,
                private logger: LoggerService,
                private inventur: InventurService,
                private router: Router) {
    }

    ngOnInit() {
        this.active.paramMap
            .map(parameter => parameter.get('id'))
            .subscribe(id => {
                this.query.send(QueryType.LeseInventar, {id: id}, ResultType.Inventar);
            })
    }

    haushaltsbuchAnlegen() {
        console.info("Haushaltsbuch anlegen")
    }

    inventurWiederholen() {
        this.inventur.beginnen();
    }
}
