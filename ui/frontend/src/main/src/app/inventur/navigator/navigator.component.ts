import { NgRedux } from '@angular-redux/store';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { AppState } from '../../store/model';
import { InventurGruppe } from '../shared/gruppen.redux';

@Component({
  selector: 'app-navigator',
  templateUrl: './navigator.component.html',
  styleUrls: ['./navigator.component.scss']
})
export class NavigatorComponent implements OnInit {

  gruppen: Observable<InventurGruppe>;
  inventurId: Observable<string>

  constructor(private store: NgRedux<AppState>, active: ActivatedRoute) {
    this.gruppen = this.store.select(s => s.inventurGruppen.gruppen)
      this.inventurId = active.params.map(p => p.id)
  }

  ngOnInit() {
  }

}
