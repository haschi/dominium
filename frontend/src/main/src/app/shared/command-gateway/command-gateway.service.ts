import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { CommandMessage, CommandMessageAction, CommandResponse } from './command-bus.model';
import { dispatch, select } from '@angular-redux/store';
import { CommandBusActionsService } from './command-bus-actions.service';

@Injectable()
export class CommandGatewayService {

    constructor(private http: HttpClient, private aktionen: CommandBusActionsService) {
    }

    @select(['command', 'sendet'])
    sendet$: Observable<boolean>;

    @select(['command', 'message'])
    message$: Observable<CommandMessage>;

    @select(['command', 'response'])
    status$: Observable<CommandResponse>;

    post(action: CommandMessageAction): Observable<HttpResponse<Object>> {
        return this.http.post("/gateway/command", action.message, {observe: 'response'});
    }

    @dispatch()
    send(type: string, payload: any, meta: any): CommandMessageAction {
        return this.aktionen.angefordert(type, payload, meta)
    }
}
