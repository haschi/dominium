import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { CommandMessage, CommandMessageAction, CommandResponse } from './command-gateway.model';
import { dispatch, select } from '@angular-redux/store';
import { CommandGatewayActionsService } from './command-gateway-actions.service';
import { CommandType } from '../../inventur/command-type';
import { LoggerService } from '../logger.service';

@Injectable()
export class CommandGatewayService {

    constructor(
        private http: HttpClient,
        private aktionen: CommandGatewayActionsService,
        private logger: LoggerService
    ) {
    }

    @select(['command', 'sendet'])
    sendet$: Observable<boolean>;

    @select(['command', 'message'])
    message$: Observable<CommandMessage>;

    @select(['command', 'response'])
    status$: Observable<CommandResponse>;

    post(action: CommandMessageAction): Observable<HttpResponse<Object>> {
        this.logger.log(`Send Command ${action.message.type}`);
        return this.http.post("/gateway/command", action.message, {observe: 'response'});
    }

    @dispatch()
    send(type: CommandType, payload: any, meta: any): CommandMessageAction {
        return this.aktionen.angefordert(type, payload, meta)
    }
}
