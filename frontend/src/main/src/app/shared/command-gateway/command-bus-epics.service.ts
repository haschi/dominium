import { Injectable } from '@angular/core';
import { createEpicMiddleware, Epic, EpicMiddleware } from 'redux-observable';
import { AppState } from '../../store/model';
import { CommandBusService } from './command-bus.service';
import { CommandAction, CommandMessageAction } from './command-bus.model';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/startWith';
import { CommandBusActions, CommandBusActionsService } from './command-bus-actions.service';

@Injectable()
export class CommandBusEpicsService {
    constructor(private aktionen: CommandBusActionsService,
                private service: CommandBusService) {
    }

    public createEpic(commandType: string): EpicMiddleware<CommandAction, AppState> {
        return createEpicMiddleware(this.createAngefordertEpic(commandType));
    }

    private createAngefordertEpic(commandType: string): Epic<CommandAction, AppState> {
        return (action$, store) => action$
            .ofType(CommandBusActions.angefordert)
            .do(action => console.info("EXECUTE COMMAND EPIC: " + JSON.stringify(action)))
            .mergeMap(action => this.service.send(action as CommandMessageAction)
                .map(response => this.aktionen.gelungen(response)))

        // TODO: Abhängig vom Ergebnis eines Command Aufrufs, müssen die fachlichen Epics
        // getriggert werden. Dazu müssen diese wissen, welche Commands erfolgreich
        // ausgeführt wurden.

        // TODO: Fehlerbehandlung!
    }
}
