import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Kategorie } from '../../shared/gruppen.redux';
import { PositionEingabe } from '../../shared/inventarposition';

@Component({
    selector: 'app-inventur-position',
    templateUrl: './posten.component.html',
    styleUrls: ['./posten.component.scss']
})
export class PostenComponent implements OnInit {

    @Input()
    kategorie: Kategorie

    @Input()
    public posten: PositionEingabe

    @Output()
    public geloescht: EventEmitter<void> = new EventEmitter<void>();

    constructor() {
    }

    ngOnInit() {
    }
}
