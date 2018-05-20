import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Kategorie } from '../../shared/gruppen.redux';

@Component({
    selector: 'app-inventur-position',
    templateUrl: './posten.component.html',
    styleUrls: ['./posten.component.scss']
})
export class PostenComponent implements OnInit {

    @Input()
    posten: FormGroup;

    @Input()
    kategorie: Kategorie

    @Output()
    public geloescht: EventEmitter<void> = new EventEmitter<void>();

    constructor() {
    }

    ngOnInit() {
    }
}
