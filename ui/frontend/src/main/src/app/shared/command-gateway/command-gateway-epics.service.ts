import { Injectable } from '@angular/core';
import { AppState } from '../../store/model';
import { CommandGatewayService } from './command-gateway.service';
import { CommandAction, CommandMessageAction } from './command-gateway.model';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/delay';
import {
    CommandGatewayActionsService,
    CommandGatewayActionType
} from './command-gateway-actions.service';
import { of } from 'rxjs/observable/of';
import { Observable } from 'rxjs/Observable';
import { TdDialogService } from '@covalent/core';
import { createEpicMiddleware, EpicMiddleware, Epic } from 'redux-observable';

@Injectable()
export class CommandGatewayEpicsService {
    constructor(private _dialogService: TdDialogService,
                private aktionen: CommandGatewayActionsService,
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
            .do(action => console.info("COMMAND EXECUTE EPIC: " + JSON.stringify(action)))
            .mergeMap(action => this.service.post(action as CommandMessageAction)
                .map(response => this.aktionen.gelungen(action.message, response))
                .catch(error => this.onError(error, action)));
    }

    private onError(error: any, action: CommandAction): Observable<CommandAction> {
        console.info("COMMAND EPIC ERROR " + JSON.stringify(error));
        if (error.status >= 500) {
            return this.openRetryDialog()
                .map((accept: boolean) => {
                    if (accept) {
                        return this.aktionen.angefordert(action.type, action.message.payload, action.message.meta)

                    } else {
                        return this.aktionen.fehlgeschlagen(action.message, error.status, "Fehler")
                    }
                }).delay(2000)
        }
        return of(this.aktionen.fehlgeschlagen(action.message, error.status, "Fehler"))
    }

    openRetryDialog(): Observable<boolean> {
        return this._dialogService.openConfirm({
            message: 'Der Server ist derzeit nicht verfügbar. Soll die Anfrage wiederholt werden',
            title: 'Verbindungsfehler', //OPTIONAL, hides if not provided
            cancelButton: 'Nein',
            acceptButton: 'Ja',
        }).afterClosed();
    }
}
