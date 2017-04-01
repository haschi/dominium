import { TestBed, inject, tick, fakeAsync, discardPeriodicTasks } from '@angular/core/testing';
import { HttpTestModule } from './httptest.module';
import { ReduxTestModule } from '../reduxtest.module';
import { JobService } from './job.service';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { ResponseOptions, Response, Headers } from '@angular/http';
import { AppState } from '../reducer';
import { NgRedux } from '@angular-redux/store';
import { JobActions } from './jobs.actions';

describe('Job', () => {
    beforeEach(fakeAsync(() => {

        TestBed.configureTestingModule(
            {
                imports: [HttpTestModule, ReduxTestModule],
                providers: [JobService]
            });
    }));

    describe('Ein neuer Job', () => {

        it('sollte erstellt werden', fakeAsync(inject([NgRedux], (store: NgRedux<AppState>) => {
            store.dispatch(JobActions.jobErstellt('location-url'));
            tick(4999);
            expect(store.getState().job).toEqual({
                location: 'location-url',
                loading: true,
                error: null,
                redirect: null
            });
            discardPeriodicTasks();
        })));

        describe('ohne Antwort vom Server', () => {
            it('wartet 5000 ms auf ein Timeout',
                fakeAsync(inject([NgRedux], (store: NgRedux<AppState>) => {
                    store.dispatch(JobActions.jobErstellt('location-url'));
                    tick(5000);
                    expect(store.getState().job).toEqual({
                        location: 'location-url',
                        loading: false,
                        error: 'Timeout has occurred',
                        redirect: null
                    });

                    discardPeriodicTasks();
                })));
        });

        describe('mit Antwort vom Server', () => {
            beforeEach(
                inject([MockBackend], (backend: MockBackend) => {
                    backend.connections.subscribe((connection: MockConnection) => {
                        connection.mockRespond(new Response(new ResponseOptions({
                            status: 200,
                            body: JSON.stringify({version: 123456}),
                            headers: new Headers({location: '/resource/4711'})
                        })));

                    });
                })
            );

            it('liefert eben diese',
                fakeAsync(inject([NgRedux], (store: NgRedux<AppState>) => {
                    store.dispatch(JobActions.jobErstellt('location-url'));
                    tick(1000);
                    expect(store.getState().job).toEqual({
                        location: 'location-url',
                        loading: false,
                        error: null,
                        redirect: '/resource/4711'
                    });

                    discardPeriodicTasks();
            })));
        });
    });
});
