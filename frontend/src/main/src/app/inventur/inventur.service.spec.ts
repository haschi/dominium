import { inject, TestBed } from '@angular/core/testing';

import { InventurService } from './inventur.service';
import { ActionsService } from '../store/actions.service';
import { NgRedux, NgReduxModule } from '@angular-redux/store';
import { APP_INITIAL_STATE, AppState } from '../store/model';
import { rootReducer } from '../store/reducers';

describe('InventurService', () => {
    beforeEach(() => {

        TestBed.configureTestingModule({
            providers: [InventurService, ActionsService],
            imports: [NgReduxModule]
        });
    });

    beforeEach(inject([NgRedux], (store: NgRedux<AppState>) => {
        store.configureStore(rootReducer, APP_INITIAL_STATE)
    }));

    it('should be created', inject([InventurService], (service: InventurService) => {
        expect(service).toBeTruthy();
    }));

    it('sollte Inventur beginnen', inject([InventurService, ActionsService, NgRedux], (service: InventurService, action: ActionsService, store: NgRedux<AppState>) => {
        let testId = "12345";
        action.begonnen(testId);

        service.inventurid$.subscribe(id => {
            console.info("CHECK Id");
            expect(id).toEqual(testId)
        })
    }))
});
