import { Component, OnInit } from '@angular/core';
import { Inventar } from '../inventar';
import { ActivatedRoute } from '@angular/router';
import { QueryGatewayService } from '../../shared/query-gateway/query-gateway.service';
import { QueryType } from '../../shared/query-gateway/query-type';
import { Observable } from 'rxjs/Observable';

import { select } from '@angular-redux/store';
import { LoggerService } from '../../shared/logger.service';

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
                private logger: LoggerService) {
    }

    ngOnInit() {
        this.active.paramMap.subscribe(p => {
            this.logger.log(`INVENTAR init id=${p.get('id')} Lese Inventar`);
            this.query.send(QueryType.LeseInventar.toString(), {id: p.get('id')}, {});
        })
    }
}
