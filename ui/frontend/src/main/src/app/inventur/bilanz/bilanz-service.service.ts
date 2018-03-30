import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Eroeffnungsbilanz } from './bilanz.model';

@Injectable()
export class BilanzServiceService {

  bilanz$: Observable<Eroeffnungsbilanz>;

  constructor() { }

}
