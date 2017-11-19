import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
    selector: 'app-inventur-position',
    templateUrl: './posten.component.html',
    styleUrls: ['./posten.component.scss']
})
export class PostenComponent implements OnInit {

    @Input()
    posten: FormGroup;

    @Output()
    public geloescht: EventEmitter<void> = new EventEmitter<void>();

    constructor() {
    }

    ngOnInit() {
    }
}
