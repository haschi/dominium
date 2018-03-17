import { inject, TestBed } from '@angular/core/testing';

import { InventurService } from './inventur.service';
import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { APP_INITIAL_STATE, AppState } from '../store/model';
import { rootReducer } from '../store/reducers';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CommandGatewayModule } from '../shared/command-gateway/command-gateway.module';
import { LoggerService } from '../shared/logger.service';
import { DEMO_APP_ROUTES } from '../routes';
import { RouterTestingModule } from '@angular/router/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HomeComponent } from '../home/home.component';
import { InventurComponent } from './inventur.component';
import { InventarComponent } from './inventar/inventar.component';

describe('InventurService', () => {
    beforeEach(() => {

        TestBed.configureTestingModule({
            declarations: [HomeComponent, InventurComponent, InventarComponent],
            providers: [InventurService, LoggerService],
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
});
