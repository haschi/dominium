import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { InventurService } from '../inventur/inventur.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    constructor(private inventur: InventurService,
                private router: Router) {
    }

    ngOnInit() {
    }

    inventurBeginnen() {
        this.inventur.beginnen();
    }
}
