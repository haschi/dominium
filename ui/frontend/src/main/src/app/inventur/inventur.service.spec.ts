import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { HttpResponseBase } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { inject, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HomeComponent } from '../home/home.component';
import { DEMO_APP_ROUTES } from '../routes';
import { CommandGatewayActionsService } from '../shared/command-gateway/command-gateway-actions.service';
import { CommandMessageAction } from '../shared/command-gateway/command-gateway.model';
import { CommandGatewayModule } from '../shared/command-gateway/command-gateway.module';
import { LoggerService } from '../shared/logger.service';
import { QueryGatewayModule } from '../shared/query-gateway/query-gateway.module';
import { QueryGatewayService } from '../shared/query-gateway/query-gateway.service';
import { QueryType } from './query-type';
import { ResultType } from './result-type';
import { APP_INITIAL_STATE, AppState } from '../store/model';
import { rootReducer } from '../store/reducers';
import { BilanzComponent } from './bilanz/bilanz.component';
import { CommandType } from './command-type';
import { InventarComponent } from './inventar/inventar.component';
import { InventurComponent } from './inventur.component';

import { InventurService } from './inventur.service';

describe('InventurService', () => {
    beforeEach(() => {

        TestBed.configureTestingModule({
            declarations: [HomeComponent, InventurComponent, InventarComponent, BilanzComponent],
            providers: [
                InventurService,
                LoggerService,
                {
                    provide: QueryGatewayService,
                    useValue: jasmine.createSpyObj('QueryGatewayService', ['send'])
                }
            ],
            imports: [NgReduxModule, HttpClientTestingModule, CommandGatewayModule, RouterTestingModule.withRoutes(DEMO_APP_ROUTES)],
            schemas: [NO_ERRORS_SCHEMA]
        });
    });

    beforeEach(inject([NgRedux], (store: NgRedux<AppState>) => {
        store.configureStore(rootReducer, APP_INITIAL_STATE)
    }));

    it('should be created', inject([InventurService], (service: InventurService) => {
        expect(service).toBeTruthy();
    }));

    describe('Nach Beginn der Inventur', () => {
        beforeEach(inject([CommandGatewayActionsService], (command: CommandGatewayActionsService) => {
            command.gelungen(
                {type: CommandType.BeginneInventur, payload: {}, meta: {}},
                {body: null, type: undefined, status: 200, clone: undefined, headers: undefined, statusText: '', url: '', ok: true})
        }))

        it('leseInventar sollte Inventar anfordern',
            inject([InventurService, QueryGatewayService],
                (service: InventurService, query: QueryGatewayService) => {
                service.leseInventar();

                const spy = query.send as jasmine.Spy;
                expect(spy).toHaveBeenCalledWith(
                    QueryType.LeseInventar,
                    jasmine.anything(),
                    ResultType.Inventar)
            }))
    })
});
