import { KonfigurationState, KONFIGURATION_INIT_STATE, konfigurationReducer } from './reducer';
import { TestBed, inject } from '@angular/core/testing';
import { Aktionen } from './Aktionen';
import { HttpTestModule } from './httptest.module';
import { NgRedux } from '@angular-redux/store';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { Response, BaseResponseOptions } from '@angular/http';
import { createStore } from 'redux';

describe('Konfiguration', () => {

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpTestModule],
            providers: [
                Aktionen,
                {
                    provide: NgRedux,
                    useFactory: () => createStore(konfigurationReducer, KONFIGURATION_INIT_STATE)
                },
                {
                    provide: KonfigurationState,
                    useFactory: (redux: NgRedux<KonfigurationState>) => redux.getState(),
                    deps: [NgRedux]
                }
            ]
        });
    });

    beforeEach(inject([Aktionen, MockBackend], (aktionen: Aktionen, backend: MockBackend) => {
        backend.connections.subscribe((connection: MockConnection) => {
            connection.mockRespond(new Response(new BaseResponseOptions().merge({
                body: JSON.stringify({
                    name: 'service',
                    version: 123456,
                    _links: []
                })
            })));
        });

        aktionen.konfigurationLaden();
    }));

    it('sollte einen Namen haben', inject([KonfigurationState], (store: KonfigurationState) => {
        expect(store.name).toBe('service');
    }));

    it('sollte eine Buildnummer haben',
            inject([KonfigurationState], (store: KonfigurationState) => {
        expect(store.version).toBe(123456);
    }));

    xit('sollte Links als Einsteigspunkte des Backends haben',
            inject([NgRedux], (store: NgRedux<KonfigurationState>) => {
        expect(store.getState()._links).toBe([]);
    }));
});
