import { inject, TestBed } from '@angular/core/testing';

import { CommandGatewayService } from './command-gateway.service';
import { CommandBusActionsService } from './command-bus-actions.service';
import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { AppState } from '../../store/model';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StoreModule } from '../../store/store.module';
import { RootEpicsService } from '../../store/root-epics.service';
import { CommandGatewayModule } from './command-gateway.module';

describe('CommandGatewayService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [NgReduxModule, HttpClientTestingModule, StoreModule, CommandGatewayModule],
            providers: [RootEpicsService]
        });
    });

    it('should be created', inject([CommandGatewayService], (service: CommandGatewayService) => {
        expect(service).toBeTruthy();
    }));

    it('Aktion angefordert sollte state auf sendet setzen',
        inject([NgRedux, CommandBusActionsService, CommandGatewayService], (store: NgRedux<AppState>, actions: CommandBusActionsService, commandBus: CommandGatewayService) => {
            console.info('EXECUTE sollte commando aus epic senden');
            store.dispatch(actions.angefordert("beginneInventur", {id: "12345"}, {}));
            commandBus.sendet$.subscribe(s => {
                console.info("KONTROLLE: " + s);
                expect(s).toBeTruthy()
            })
        }));

    it('Aktion angefordert sollte message im state setzen',
        inject([NgRedux, CommandBusActionsService, CommandGatewayService], (store: NgRedux<AppState>, actions: CommandBusActionsService, commandBus: CommandGatewayService) => {
            store.dispatch(actions.angefordert("beginneInventur", {id: "12345"}, {}));
            commandBus.message$.subscribe(m => {
                console.info("KONTROLLE: " + JSON.stringify(m));
                expect(m).toEqual({
                    type: 'beginneInventur',
                    payload: {id: "12345"},
                    meta: {}
                })
            })
        }));

    it('Aktion angefordert sollte Message an Backend senden',
        inject([NgRedux, CommandBusActionsService, CommandGatewayService, HttpTestingController], (store: NgRedux<AppState>, actions: CommandBusActionsService, commandBus: CommandGatewayService, httpMock: HttpTestingController) => {
            store.dispatch(actions.angefordert("beginneInventur", {id: "12345"}, {}));

            const request = httpMock.expectOne('/gateway/command');
            expect(request.request.method).toEqual('POST');
            expect(request.request.body).toEqual({
                type: 'beginneInventur',
                payload: {id: "12345"},
                meta: {}
            });
            httpMock.verify()
        }));

    it('Aktion angefordert sollte status setzen',
        inject([NgRedux, CommandBusActionsService, CommandGatewayService, HttpTestingController], (store: NgRedux<AppState>, actions: CommandBusActionsService, commandBus: CommandGatewayService, httpMock: HttpTestingController) => {
            store.dispatch(actions.angefordert("beginneInventur", {id: "12345"}, {}));
            const r = httpMock.expectOne('/gateway/command');
            r.flush(null, {status: 202, statusText: 'Accepted'});
            commandBus.status$.subscribe(s =>
                expect(s).toEqual({status: 202, message: 'Accepted'})
            )
        }))
});
