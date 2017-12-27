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
import { Actions } from '../../store/actions';

const isCommandMessage = (type: string) =>
    (action: CommandMessageAction): boolean =>
        action.message.type === type;

@Injectable()
export class CommandGatewayEpicsService {
    constructor(private aktionen: CommandGatewayActionsService,
                private service: CommandGatewayService) {
    }

    public createEpic(commandType: string): [EpicMiddleware<CommandAction, AppState>] {
        return [
            createEpicMiddleware(this.createAngefordertEpic(commandType)),
            createEpicMiddleware(this.createGelungenEpic())
        ];
    }

    // angefordert -> gelungen
    // angefordert -> fehlgeschlagen
    private createAngefordertEpic(commandType: string): Epic<CommandAction, AppState> {
        return (action$, store) => action$
            .ofType(CommandGatewayActionType.angefordert)
            .do(action => console.info("EXECUTE COMMAND EPIC: " + JSON.stringify(action)))
            .mergeMap(action => this.service.post(action as CommandMessageAction)
                .map(response => this.aktionen.gelungen(action.message, response)))

        // TODO: Fehlerbehandlung!
    }

    private createGelungenEpic(): Epic<CommandMessageAction, AppState> {
        return (action$, store) => action$
            .ofType(CommandGatewayActionType.gelungen)
            .filter(message => isCommandMessage('com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur')(message))
            .map(message => ({type: Actions.InventurBegonnen, message: message.message}))
    }
}
