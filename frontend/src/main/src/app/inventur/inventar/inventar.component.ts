import { Component, Input, OnInit } from '@angular/core';
import { Inventar } from '../inventar';

@Component({
    selector: 'app-inventar',
    templateUrl: './inventar.component.html',
    styleUrls: ['./inventar.component.scss']
})
export class InventarComponent implements OnInit {

    @Input()
    inventar: Inventar;

    constructor() {
    }

    ngOnInit() {
    }
}
