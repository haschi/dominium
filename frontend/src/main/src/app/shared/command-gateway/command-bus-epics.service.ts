import { Injectable } from '@angular/core';
import { createEpicMiddleware, Epic, EpicMiddleware } from 'redux-observable';
import { AppState } from '../../store/model';
import { CommandGatewayService } from './command-gateway.service';
import { CommandAction, CommandMessageAction } from './command-bus.model';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/filter';
import { CommandBusActions, CommandBusActionsService } from './command-bus-actions.service';
import { Actions } from '../../store/actions';

const isCommandMessage = (type: string) =>
    (action: CommandMessageAction): boolean =>
        action.message.type === type;

@Injectable()
export class CommandBusEpicsService {
    constructor(private aktionen: CommandBusActionsService,
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
            .ofType(CommandBusActions.angefordert)
            .do(action => console.info("EXECUTE COMMAND EPIC: " + JSON.stringify(action)))
            .mergeMap(action => this.service.post(action as CommandMessageAction)
                .map(response => this.aktionen.gelungen(action.message, response)))

        // TODO: Abhängig vom Ergebnis eines Command Aufrufs, müssen die fachlichen Epics
        // getriggert werden. Dazu müssen diese wissen, welche Commands erfolgreich
        // ausgeführt wurden.

        // TODO: Fehlerbehandlung!
    }

    private createGelungenEpic(): Epic<CommandMessageAction, AppState> {
        return (action$, store) => action$
            .ofType(CommandBusActions.gelungen)
            .filter(message => isCommandMessage('com.github.haschi.dominium.haushaltsbuch.core.model.commands.BeginneInventur')(message))
            .map(message => ({type: Actions.InventurBegonnen, message: message.message}))
    }
}
