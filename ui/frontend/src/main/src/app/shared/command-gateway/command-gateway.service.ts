import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { commandAngefordert, CommandMessageAction } from './command.redux';
import { dispatch, select } from '@angular-redux/store';
import { CommandType } from '../../inventur/command-type';
import { LoggerService } from '../logger.service';
import { CommandMessage, CommandResponse } from './command.redux';

@Injectable()
export class CommandGatewayService {

    constructor() {}

    @select(['command', 'sendet'])
    sendet$: Observable<boolean>;

    @select(['command', 'message'])
    message$: Observable<CommandMessage>;

    @select(['command', 'response'])
    status$: Observable<CommandResponse>;

    @dispatch()
    send(type: CommandType, payload: any, meta: any): CommandMessageAction {
        return commandAngefordert(type, payload, meta)
    }
}
