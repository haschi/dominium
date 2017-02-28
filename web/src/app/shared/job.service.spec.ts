import { AppState } from '../reducer';
import { TestBed, inject, async, tick, fakeAsync } from '@angular/core/testing';
import { NgRedux } from '@angular-redux/store';
import { HttpTestModule } from '../httptest.module';
import { ReduxTestModule } from '../reduxtest.module';
import { Ereignis } from '../ereignis';
import { JobService } from './job.service';
import { Observable } from 'rxjs/Observable';

describe('Job', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule(
            {
                imports: [HttpTestModule, ReduxTestModule],
                providers: [Ereignis, JobService]
            });
    }));

    it('sollte keine gestarteten Jobs haben', async(
        (inject([JobService, Ereignis], (jobs: JobService, e: Ereignis) => {
            // fail('Das war nix');

            e.jobGestartet('hallo');

            let hotjobs = jobs.laufend.isEmpty().publish();

            jobs.laufend.isEmpty();
            Observable.empty().subscribe(
                (empty: boolean) => {
                    console.info('Ausgeführt 1');
                    expect(empty).toBeTruthy();
                },
                (error: any) => fail('ERROR: ' + error),
                () => console.info('DONE')
            );

            hotjobs.connect();
    }))));

    describe('Ein neuer Job', () => {

        beforeEach(async(inject([Ereignis], (ereignis: Ereignis) => {
            ereignis.jobGestartet('location-url');
        })));

        it('sollte gestartet werden',
        async(inject([JobService], (jobs: JobService) => {
            jobs.laufend.subscribe(l => expect(l).toEqual('location-url'));
        })));
    });

    describe('Ein gestarteter Job', () => {
        it('sollte abgebrochen werden können', () => {});
        it('sollte bis zum Erfolg die Job Resource pollen', () => {});
        it('sollte Timeout Fehler erzeugen', () => {});
    });
});
