import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { CommandMessage, CommandMessageAction, CommandResponse } from './command-bus.model';
import { select } from '@angular-redux/store';

@Injectable()
export class CommandBusService {

    constructor(private http: HttpClient) {
    }

    @select(['command', 'sendet'])
    sendet$: Observable<boolean>;

    @select(['command', 'message'])
    message$: Observable<CommandMessage>;

    @select(['command', 'response'])
    status$: Observable<CommandResponse>;

    send(action: CommandMessageAction): Observable<HttpResponse<Object>> {
        return this.http.post("/gateway/command", action.message, {observe: 'response'});
    }
}
