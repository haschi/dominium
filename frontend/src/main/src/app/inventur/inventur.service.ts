import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { select } from '@angular-redux/store';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ActionsService } from '../store/actions.service';

@Injectable()
export class InventurService {

    @select(['inventur', 'inventurId'])
    inventurid$: Observable<any>;

    constructor(private http: HttpClient, private aktionen: ActionsService) {
    }

    beginneInventur() {
        this.http.post('/gateway/inventur', null, {observe: 'response', responseType: 'text'})
            .map((response: HttpResponse<string>) => response.headers.get('Aggregatkennung'))
            .subscribe((id: string) => this.aktionen.begonnen(id));
    }
}
