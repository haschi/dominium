import {
    TestBed,
    inject,
    async,
    tick,
    fakeAsync,
    discardPeriodicTasks
} from '@angular/core/testing';
import { HttpTestModule } from '../httptest.module';
import { ReduxTestModule } from '../reduxtest.module';
import { JobService } from './job.service';
import { Observable } from 'rxjs/Observable';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { ResponseOptions, Response, Headers } from '@angular/http';
import { BeendeterJob, FehlgeschlagenerJob } from './jobs.redux';
import { AppState } from '../reducer';
import { NgRedux } from '@angular-redux/store';
import { jobGestartet } from './jobs.actions';

describe('Job', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule(
            {
                imports: [HttpTestModule, ReduxTestModule],
                providers: [JobService]
            });
    }));

    xit('sollte keine gestarteten Jobs haben', async(
        (inject([JobService, NgRedux], (jobs: JobService, store: NgRedux<AppState>) => {
            // fail('Das war nix');

            store.dispatch(jobGestartet('hallo'));

            let hotjobs = jobs.laufend.isEmpty().publish();

            jobs.laufend.isEmpty();
            Observable.empty().subscribe(
                (empty: boolean) => {
                    expect(empty).toBeTruthy();
                },
                (error: any) => fail('ERROR: ' + error)
            );

            hotjobs.connect();
        }))));

    describe('Ein neuer Job', () => {

        beforeEach(async(inject([NgRedux], (store: NgRedux<AppState>) => {
            store.dispatch(jobGestartet('location-url'));
        })));

        xit('sollte gestartet werden',
            async(inject([JobService], (jobs: JobService) => {
                jobs.laufend.subscribe(l => expect(l).toEqual('location-url'));
            })));
    });

    describe('Ein gestarteter Job', () => {
        beforeEach(
            inject([JobService], (jobs: JobService) => {
                jobs.init();
            })
        );

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

        xit('sollte abgebrochen werden können', () => {

        });

        it('sollte bis zum Erfolg die Job Resource pollen',
            fakeAsync(inject([JobService, NgRedux],
                (jobs: JobService, store: NgRedux<AppState>) => {
                store.dispatch(jobGestartet('location-url'));
                    let completed = false;

                    jobs.beendet.subscribe(
                        (job: BeendeterJob) => {
                            expect(job.location).toEqual('/resource/4711');
                            completed = true;
                        });

                    tick(1000);
                    // Important
                    // https://github.com/angular/angular/issues/10127
                    discardPeriodicTasks();
                    expect(completed).toBeTruthy();
                })
            ));

        xit('sollte Timeout Fehler erzeugen', async(inject([JobService], (jobs: JobService) => {
            jobs.abgebrochen.subscribe(
                (job: FehlgeschlagenerJob) => expect(job.grund).toEqual('Timeout'),
                () => fail('Unerwarteter Fehler'));
        })));
    });
});
