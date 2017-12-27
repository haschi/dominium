import { Injectable } from '@angular/core';
import { createEpicMiddleware, Epic, EpicMiddleware } from 'redux-observable';
import { AppState } from '../../store/model';
import { CommandGatewayService } from './command-gateway.service';
import { CommandAction, CommandMessageAction } from './command-gateway.model';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/filter';
import {
    CommandGatewayActionsService,
    CommandGatewayActionType
} from './command-gateway-actions.service';

@Injectable()
export class CommandGatewayEpicsService {
    constructor(private aktionen: CommandGatewayActionsService,
                private service: CommandGatewayService) {
    }

    public createEpic(): [EpicMiddleware<CommandAction, AppState>] {
        return [
            createEpicMiddleware(this.createAngefordertEpic())
        ];
    }

    // angefordert -> gelungen
    // angefordert -> fehlgeschlagen
    private createAngefordertEpic(): Epic<CommandAction, AppState> {
        return (action$) => action$
            .ofType(CommandGatewayActionType.angefordert)
            .do(action => console.info("EXECUTE EPIC: " + JSON.stringify(action)))
            .mergeMap(action => this.service.post(action as CommandMessageAction)
                .map(response => this.aktionen.gelungen(action.message, response)))

        // TODO: Fehlerbehandlung!
    }
}
