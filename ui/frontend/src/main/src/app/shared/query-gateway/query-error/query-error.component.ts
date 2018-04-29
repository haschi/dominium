import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { NgRedux, select } from '@angular-redux/store';
import { QueryGatewayService } from '../query-gateway.service';
import { AppState } from '../../../store/model';
import { QueryMessage } from '../query.redux';

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

  @select(['query', 'message'])
  private message$: Observable<QueryMessage>

  wiederholen() {

    const first: Observable<QueryMessage> = this.message$.take(1)

      // TODO: Das ist nicht ganz so gut. Noch mal genau hinschauen
        first.subscribe(m =>
      this.gateway.send(
          m.type,
          m.payload,
          m.result));
  }
}
