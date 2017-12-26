import { Injectable } from '@angular/core';
import { createEpicMiddleware, Epic, EpicMiddleware } from 'redux-observable';
import { AppState } from '../store/model';
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

        // .ofType(CommandBusActions.angefordert)
        // .switchMap(() => this.service.send()
        //     .map(() => this.actions))
        //
        // .filter(action => actionIsForCorrectAnimalType(animalType)(action))
        // .filter(() => animalsNotAlreadyFetched(animalType, store.getState()))
        // .switchMap(() => this.service.getAll(animalType)
        //     .map(data => this.actions.loadSucceeded(animalType, data))
        //     .catch(response => of(this.actions.loadFailed(animalType, {
        //         status: '' + response.status,
        //     })))
        //     .startWith(this.actions.loadStarted(animalType)));
    }
}
