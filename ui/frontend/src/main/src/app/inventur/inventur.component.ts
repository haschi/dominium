import { DataSource, SelectionModel } from '@angular/cdk/collections';
import {
    ChangeDetectionStrategy,
    Component,
    HostListener,
    OnDestroy,
    OnInit,
    ViewChild
} from '@angular/core';
import { MatDialog, MatTable } from '@angular/material';
import { ActivatedRoute, NavigationEnd, NavigationStart, Params, Router } from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/withLatestFrom';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { Subscription } from 'rxjs/Subscription';
import { EingabeDialog } from './eingabe-dialog.component';
import { InventurService } from './inventur.service';
import { Gruppe, InventurGruppe } from './shared/gruppen.redux';
import { Eingabe } from './shared/inventar-eingabe.redux';
import { PositionEingabe } from './shared/inventarposition';

interface Koordinate{
    titel: string;
    untertitel: string;
}

@Component({
    selector: 'app-inventur',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './inventur.component.html',
    styleUrls: ['./inventur.component.scss']
})
export class InventurComponent implements OnInit, OnDestroy {

    eingaben: Eingabe[];
    inventurGruppen$: Observable<InventurGruppe>;
    anzeigen$: Observable<boolean>
    inventurId$: Observable<string>
    posten$: Observable<Eingabe[]>
    auswahl$: Observable<boolean>
    gruppe$: Observable<Koordinate>

    private zeilenSubscription: Subscription;
    private zeilen: number;
    constructor(
            private dialog: MatDialog,
            private active: ActivatedRoute,
            private router: Router,
            private inventurService: InventurService) {

        this.inventurGruppen$ = this.inventurService.gruppen$

        this.anzeigen$ = this.inventurService.gruppen$
            .map(gruppen => gruppen !== InventurService.leereGruppen)

        this.gruppe$ = this.active.params
            .withLatestFrom(this.inventurService.gruppen$, (params, gruppen) => {
                console.info(`suche gruppe ${params.gruppe} in ${JSON.stringify(gruppen)}`)
                const gruppe: Gruppe = gruppen[params.gruppe];
                console.info(`suche kategorie ${params.kategorie} in gruppe ${JSON.stringify(gruppe)}`)
                const kategorie = gruppe.kategorien[params.kategorie]
                const titel = this.titelFuerGruppe(params.gruppe)
                console.info(`gefunden: ${titel}, ${kategorie.kategorie}`)
                return {titel: titel, untertitel: kategorie.kategorie}
            })
        this.inventurId$ = this.active.params.map(p => p.id)

        this.posten$ =
            this.active.params
                .map(params => {return {gruppe: params.gruppe, kategorie: Number(params.kategorie)}})
                .flatMap(params => this.inventurService.eingabe$(params.gruppe, params.kategorie), (outer, inner) => inner)

        this.auswahl$ = this.active.params
            .map(params => params.gruppe != null && params.kategorie != null)

        this.zeilenSubscription = this.posten$.subscribe(eingaben => {
            this.zeilen = eingaben.length
            this.eingaben = eingaben;
        })
    }

    private titelFuerGruppe(gruppe: string): string {
        switch(gruppe) {
        case 'anlagevermoegen': return "Anlagevermögen";
        case 'umlaufvermoegen' : return "Umlaufvermögen";
        case 'schulden': return "Schulden";
        default: return ''
        }
    }
    selection = new SelectionModel<Eingabe>(true, []);

    isAllSelected() {

        const numSelected = this.selection.selected.length;
        const numRows = this.zeilen
        return numSelected === numRows;
    }

    masterToggle() {
        this.isAllSelected() ?
            this.selection.clear() :
        this.eingaben.forEach(row => this.selection.select(row))
    }

    auswahlLoeschen() {
        this.inventurService.entfernen(this.selection.selected)
    }

    ngOnInit() {
    }

    koordinate(params: Params, gruppen: InventurGruppe): Koordinate {
        const gruppe = gruppen[params.gruppe];
        const kategorie = gruppe.kategorien[Number(params.kategorie)];

        return {
            //inventurId: params.id,
            //gruppe: gruppe.bezeichnung,
            //kategorie: Number(params.kategorie),
            titel: '',
            untertitel: kategorie.kategorie
        }
    }

    openDialog() {
        const config = {
            gruppe: this.active.snapshot.params.gruppe,
            kategorie: Number(this.active.snapshot.params.kategorie)
        }

        this.dialog.open(EingabeDialog, {
            data: config
        })
    }

    @HostListener('document:keypress', ['$event'])
    tastatureingabe($event: KeyboardEvent): boolean {
        if ($event.key === '+') {

                this.openDialog()
            return false;
        }

        return true
    }

    ngOnDestroy(): void {
        this.zeilenSubscription.unsubscribe()
    }
}

