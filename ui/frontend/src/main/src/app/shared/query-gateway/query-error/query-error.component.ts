import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { QueryMessage } from '../query-gateway.model';
import { NgRedux, select } from '@angular-redux/store';
import { QueryGatewayService } from '../query-gateway.service';
import { AppState } from '../../../store/model';

@Component({
  selector: 'app-query-error',
  templateUrl: './query-error.component.html',
  styleUrls: ['./query-error.component.scss']
})
export class QueryErrorComponent implements OnInit {

  constructor(private gateway: QueryGatewayService, private state: NgRedux<AppState>) { }

  ngOnInit() {
  }

  @select(['query'])
  queryState$: Observable<QueryMessage>;

  @select(['query', 'response', 'status'])
  status$: Observable<number>;

  wiederholen() {

    this.state.select(s => s.query.message)
        .take(1)
        .subscribe(m =>
      this.gateway.send(
          m.type,
          m.payload,
          m.result));
  }
}
