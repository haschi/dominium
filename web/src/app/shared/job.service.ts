import { Injectable } from '@angular/core';
import { select } from '@angular-redux/store';
import { AppState } from '../reducer';
import { Observable } from 'rxjs';
import { Http, Response } from '@angular/http';
import { FehlgeschlagenerJob, BeendeterJob } from './jobs.redux';
import { NgRedux } from '@angular-redux/store';
import { jobBeendet, jobFehlgeschlagen } from './jobs.actions';

@Injectable()
export class JobService {
    @select((s: AppState) => s.job.neu)
    job$: Observable<string>;

    get laufend(): Observable<string> {
        return this.job$
            .filter(j => j != null)
            .map(j => j);
    }

    @select((s: AppState) => s.job.fehler)
    abgebrochen$: Observable<FehlgeschlagenerJob>;

    get abgebrochen(): Observable<FehlgeschlagenerJob> {
        return this.abgebrochen$
            .filter(x => x != null);
    }

    @select((s: AppState) => s.job.beendet)
    beendet$: Observable<BeendeterJob>;

    get beendet(): Observable<BeendeterJob> {
        return this.beendet$
            .filter(x => x != null);
    }

    constructor(private http: Http, private store: NgRedux<AppState>) {
    }

    init() {
        this.job$
            .filter((job: string) => job != null)
            .map((job: string) => job)
            .flatMap((job: string) => {

                return Observable.interval(1000)
                    .switchMap(() => this.http.get(job))
                    .filter((response: Response) => {
                        return response.status === 200;
                    })
                    .first()
                    .timeout(5000)
                    .map(r => {
                        return <RunningJob>{job: job, response: r};
                    });
            })
            .subscribe(
                (job: RunningJob) => {
                    this.store.dispatch(
                        jobBeendet(job.job, job.response.headers.get('Location')));
                },
                (err) => {
                    this.store.dispatch(
                        jobFehlgeschlagen(null, err));
                }
            );
    }
}

interface RunningJob {
    job: string;
    response: Response;
}
