import { inject, TestBed } from '@angular/core/testing';

import { InventurService } from './inventur.service';
import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { APP_INITIAL_STATE, AppState } from '../store/model';
import { rootReducer } from '../store/reducers';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CommandGatewayModule } from '../shared/command-gateway/command-gateway.module';

describe('InventurService', () => {
    beforeEach(() => {

        TestBed.configureTestingModule({
            providers: [InventurService],
            imports: [NgReduxModule, HttpClientTestingModule, CommandGatewayModule]
        });
    });

    beforeEach(inject([NgRedux], (store: NgRedux<AppState>) => {
        store.configureStore(rootReducer, APP_INITIAL_STATE)
    }));

    it('should be created', inject([InventurService], (service: InventurService) => {
        expect(service).toBeTruthy();
    }));
});
