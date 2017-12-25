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

    let store: NgRedux<AppState>

    beforeEach(inject([NgRedux], (store: NgRedux<AppState>) => {

        store.configureStore(rootReducer, APP_INITIAL_STATE)
        this.store = store
    }));

    it('should be created', inject([InventurService], (service: InventurService) => {
        expect(service).toBeTruthy();
    }));

    it('sollte Inventur beginnen', inject([InventurService, ActionsService, NgRedux], (service: InventurService, action: ActionsService, store: NgRedux<AppState>) => {
        let state = store.getState();
        let testId = "12345";
        this.store.dispatch(action.begonnen(testId));
        expect(this.store.getState().inventur.inventurId).toEqual(testId);
        // service.beginnen();
        // expect(service.inventurid$).toEqual(testId)
    }))
});
