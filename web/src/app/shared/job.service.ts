import { Injectable } from '@angular/core';
import { select } from '@angular-redux/store';
import { AppState, FehlgeschlagenerJob, BeendeterJob } from '../reducer';
import { Observable } from 'rxjs';
import { Http, Response } from '@angular/http';
import { Ereignis } from '../ereignis';

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

    constructor(private http: Http, private ereignis: Ereignis) {
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
                    this.ereignis.jobBeendet(job.job, job.response.headers.get('Location'));
                },
                (err) => {
                    this.ereignis.jobFehlgeschlagen(null, err);
                }
            );
    }
}

interface RunningJob {
    job: string;
    response: Response;
}
