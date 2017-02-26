import { AppState } from '../reducer';
import { TestBed, inject, async } from '@angular/core/testing';
import { NgRedux } from '@angular-redux/store';
import { HttpTestModule } from '../httptest.module';
import { ReduxTestModule } from '../reduxtest.module';
import { Ereignis } from '../ereignis';

describe('Job', () => {
    beforeEach(() => {
        TestBed.configureTestingModule(
            {
                imports: [HttpTestModule, ReduxTestModule],
                providers: [Ereignis]
            });
    });

    describe('Ein neuer Job', () => {

        beforeEach(async(inject([Ereignis], (ereignis: Ereignis) => {
            ereignis.jobGestartet('location-url');
        })));

        it('sollte gestartet werden', async(inject([NgRedux], (store: NgRedux<AppState>) => {
            expect(store.getState().job).toEqual({location: 'location-url'});
        })));
    });

    describe('Ein gestarteter Job', () => {
        it('sollte abgebrochen werden können', () => {});
        it('sollte bis zum Erfolg die Job Resource pollen', () => {});
        it('sollte Timeout Fehler erzeugen', () => {});
    });
});
