import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ActivatedRoute, NavigationEnd, NavigationStart, Params, Router } from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/withLatestFrom';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { EingabeDialog } from './eingabe-dialog.component';
import { InventurService } from './inventur.service';
import { InventurGruppe } from './shared/gruppen.redux';
import { Eingabe } from './shared/inventar-eingabe.redux';
import { PositionEingabe } from './shared/inventarposition';

interface Koordinate{
    inventurId: string;
    gruppe: string;
    kategorie: number;
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

    // @ViewChildren(GruppeComponent)
    // private gruppen: QueryList<GruppeComponent>;

    // private aktuelleGruppe: number = 0;
    // private aktuelleKategorie: number = 0;

    // Das zu bearbeitende Formular, wird geändert durch
    // Wechsel der aktiven Route oder durch Hinzufügen einer
    // neuen Zeile
    // form$: Observable<FormArray>

    // Fügt eine neue Zeile zum Formular hinzu.
    // input$: Subject<Eingabe> = new Subject<Eingabe>();

    // Alle Gruppen und deren Kategorien
    inventurGruppen$: Observable<InventurGruppe>;
    anzeigen$: Observable<boolean>
    inventurId$: Observable<string>
    posten$: Observable<Eingabe[]>
    auswahl$: Observable<boolean>

    // Gruppe und Kategorie, die gerade bearbeitet wird.
    // koordinate$: Observable<Koordinate>

    // Koordinate der nächsten Gruppe für die Navigation
    // next$: Observable<Koordinate>

    constructor(
            private dialog: MatDialog,
            private active: ActivatedRoute,
            private router: Router,
            private inventurService: InventurService) {

        this.inventurGruppen$ = this.inventurService.gruppen$

        this.anzeigen$ = this.inventurService.gruppen$
            .map(gruppen => gruppen !== InventurService.leereGruppen)

        this.inventurId$ = this.active.params.map(p => p.id)

        this.posten$ =
            this.active.params
            //  .filter(event => event instanceof NavigationStart)
            // .map(event => event as NavigationStart)
            // .do(params => console.info("Navigated"))
            //.map(params => {return {gruppe: params.gruppe, kategorie: Number(params.kategorie)}})
            // .withLatestFrom(this.inventurService.eingabe$, (filter, eingabe) => {
            //     console.info(`filtering: ${JSON.stringify(eingabe)}`)
            //     return eingabe
            //         .map(element => element.position)
            // })
                .map(params => {return {gruppe: params.gruppe, kategorie: Number(params.kategorie)}})
                .flatMap(params => this.inventurService.eingabe$(params.gruppe, params.kategorie), (outer, inner) => inner)
            // .do(value => {console.info(JSON.stringify(value))})
            // .catch(error => {
            //     console.error('ERROR: '+ JSON.stringify(error))
            //     return of([])})

        this.auswahl$ = this.active.params
            .map(params => params.gruppe != null && params.kategorie != null)
    }

    // startSubscription: Subscription;
    // stopSubscription: Subscription;
    // neuSubscription: Subscription;

    ngOnInit() {

        // this.form$ = this.active.params
        //     .map(params => {return {inventurId: params.id, gruppe: params.gruppe, kategorie: Number(params.kategorie)}})
        //     .withLatestFrom(this.store.select(s => s.inventureingabe.eingaben), (koordinaten, eingaben: Eingabe[]) => {
        //          return this.builder.array(
        //              eingaben.filter(eingabe => eingabe.gruppe === koordinaten.gruppe && eingabe.kategorie === koordinaten.kategorie)
        //                  .map(eingabe => eingabe.position)
        //                  .map(position => this.zeileErzeugen(position.position, position.waehrungsbetrag.betrag, position.waehrungsbetrag.waehrung)))
        //      })

        // falls die Navigation beginnt, speichere die Eingabe
        // this.startSubscription = this.router.events
        //     .filter(event => event instanceof NavigationStart)
        //     .map(event => event as NavigationStart)
        //     .withLatestFrom(this.form$.filter(f => f.valid), (event, form: FormArray) => {
        //         return {event: event, value: form.value}
        //     })
        //     .withLatestFrom(this.active.params, (result, params) => {
        //         return {event: result.event, value: result.value, id: params.id, gruppe: params.gruppe, kategorie: params.kategorie}
        //     })
        //     .subscribe(result => {
        //         console.log("Navigation START to " + result.event.url)
        //         console.log("Speichern: " + JSON.stringify(result.value))
        //         var x = result.value
        //         console.info(`speichern(${result.gruppe}, ${result.kategorie}, ${JSON.stringify(x)})`);
        //         this.eingabe.hinzufügen(result.gruppe, result.kategorie, x);
        //     })

        // falls Navigation beendet ist, lade Daten aus Speicher
        // this.stopSubscription = this.active.params
        //     .map(params => {
        //         return {inventurId: params.id, gruppe: params.gruppe, kategorie: Number(params.kategorie)}
        //     })
        //     .subscribe(result => {
        //         console.info(`laden(${result.gruppe}, ${result.kategorie})`)
        //     })

        // falls Kategorie ausgewählt wird, ermittle die aktuelle Kategorie
        // this.koordinate$ =this.active.params
        //     .filter((p: Params) => p['gruppe'] !== undefined && p['kategorie'] !== undefined)
        //     .withLatestFrom(this.inventurGruppen$, this.koordinate)

        // falls Kategorie ausgewählt wird, ermittle den Link für die nächste Kategorie
        // this.next$ = this.active.params.withLatestFrom(this.inventurGruppen$, (params, gruppen) => {
        //     const gruppe = params.gruppe;
        //     const kategorie = Number(params.kategorie);
        //     const inventurId = params.id;
        //
        //     var x : Gruppe;
        //     var n : string = gruppe;
        //
        //     if (gruppe === 'anlagevermoegen')
        //     {
        //         x = gruppen.anlagevermoegen;
        //     } else if (gruppe === 'umlaufvermoegen') {
        //         x = gruppen.umlaufvermoegen;
        //     } else {
        //         x = gruppen.schulden
        //     }
        //
        //     var max = x.kategorien.length;
        //     var next = (kategorie + 1) % max;
        //
        //     if (kategorie + 1 === max) {
        //         if (gruppe === 'anlagevermoegen')
        //         {
        //             n = 'umlaufvermoegen';
        //             x = gruppen.umlaufvermoegen;
        //         } else if (gruppe === 'umlaufvermoegen') {
        //             n = 'schulden';
        //             x = gruppen.schulden;
        //         } else {
        //             n = 'anlagevermoegen';
        //             x = gruppen.anlagevermoegen; // besser nix
        //         }
        //     }
        //
        //     return {
        //         inventurId: inventurId,
        //         gruppe: n,
        //         kategorie: next,
        //         titel: 'Gruppe',
        //         untertitel: 'Kategorie'
        //     }
        // })

        // falls neue Zeile, speichere Eingabe + neue Zeile
        // this.neuSubscription = this.input$.withLatestFrom(this.koordinate$, (input: Eingabe, koordinate: Koordinate) => {
        //     return zeileHinzufügen(koordinate.gruppe, koordinate.kategorie)
        // }).subscribe(action => this.store.dispatch(action));
    }

    koordinate(params: Params, gruppen: InventurGruppe): Koordinate {
        console.info(JSON.stringify(gruppen))
        console.info(JSON.stringify(params))
        const gruppe = gruppen[params.gruppe];
        const kategorie = gruppe.kategorien[Number(params.kategorie)];

        return {
            inventurId: params.id,
            gruppe: gruppe.bezeichnung,
            kategorie: Number(params.kategorie),
            titel: '',
            untertitel: kategorie.kategorie
        }
    }

    openDialog() {
        this.dialog.open(EingabeDialog, {
            data: {
                gruppe: this.active.snapshot.params.gruppe,
                kategorie: Number(this.active.snapshot.params.kategorie)
            }
        })
    }

    // hinzufuegen() {
    //     const eingabe: Eingabe = {gruppe: '', kategorie: 0, position: {position: '', waehrungsbetrag: {betrag: '', waehrung: ''}} }
    //     this.input$.next(eingabe)
    // }

    // @HostListener('document:keypress', ['$event'])
    // tastatureingabe($event: KeyboardEvent): boolean {
    //     if ($event.key === '+') {
    //
    //             this.hinzufuegen();
    //         return false;
    //     }
    //
    //     return true
    // }

    // zeileHinzufuegen() {
    //     const group = this.zeileErzeugen();
    // }

    // private zeileErzeugen(position: string = '', betrag: string = '', waehrung: string = 'EUR'): FormGroup {
    //     const waehrungsbetrag = new FormGroup({
    //         betrag: new FormControl(betrag, Validators.required),
    //         waehrung: new FormControl(waehrung, Validators.required)
    //     });
    //
    //     const group = new FormGroup({
    //         position: new FormControl(position, Validators.required),
    //         waehrungsbetrag: waehrungsbetrag,
    //     });
    //     return group;
    // }

    // loeschen(index: number) {
    //     this.formular.removeAt(index);
    // }

    ngOnDestroy(): void {
        // if(this.startSubscription) this.startSubscription.unsubscribe();
        // if (this.stopSubscription) this.stopSubscription.unsubscribe();
        // if (this.neuSubscription) this.neuSubscription.unsubscribe();
    }
}

