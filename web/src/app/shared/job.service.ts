import { Injectable } from '@angular/core';
import { select } from '@angular-redux/store';
import { AppState, JobState } from '../reducer';
import { Observable } from 'rxjs';
import { Http, Response } from '@angular/http';
import { NgRedux } from '@angular-redux/store';

@Injectable()
export class JobService {
    @select((s: AppState) => s.job)
    job$: Observable<JobState>;


    get laufend(): Observable<string> {
        return this.job$
            .filter(j => j.location != null)
            .map(j => j.location);
    }

    constructor(private http: Http, private store: NgRedux<AppState>) {}
    init() {
        this.job$.subscribe((j: JobState) => {
            console.info('Job geändert: ' + j.location);
        });

        this.job$
            .filter((job: JobState) => job.location != null)
            .map((job: JobState) => job)
            .flatMap((job: JobState, index: number) => {
                console.info('job index: ' + index);
                return Observable.interval(1000)
                    .switchMap(() => this.http.get(job.location))
                    .filter((response: Response) => response.status === 200)
                    .first();
            })
            .subscribe( (response: Response) => {
                console.log('Datenänderung im JobSerivce');
                console.info('Response for ' + response.url + ' = ' + response.status);
            });
    }
}
