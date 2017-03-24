import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Http, Response } from '@angular/http';

@Injectable()
export class JobService {

    constructor(private http: Http) {}

    poll(location: string): Observable<RunningJob> {
        return Observable.interval(1000)
            .switchMap(() => this.http.get(location))
            .filter((response: Response) => {
                return response.status === 200;
            })
            .first()
            .timeout(5000)
            .map(r => {
                return <RunningJob>{location: location, response: r};
            });
    }

    init() {
        // this.neu$
        //     .forEach((job: Job[]) => console.log('Job erstellt: ' + job));
        //
        // this.gestartet$
        //     .forEach((job: Job[]) => console.log('Job gestartet: ' + job));
        //
        // this.beendet$
        //     .forEach((job: BeendeterJob) => console.log('Job beendet: ' + job.location));
        //
        // this.neu$
        //     .flatMap((job: Job[]) => { return this.poll(job[0]); })
        //     .subscribe(
        //         (job: RunningJob) => {
        //             this.store.dispatch(
        //                 this.actions.jobBeendet(job.job, job.response.headers.get('Location')));
        //         },
        //         (err) => {
        //             this.store.dispatch(
        //                 this.actions.jobFehlgeschlagen(null, err));
        //         }
        //     );
    }
}

export interface RunningJob {
    location: string;
    response: Response;
}
