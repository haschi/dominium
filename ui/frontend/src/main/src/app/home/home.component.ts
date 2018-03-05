import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { InventurService } from '../inventur/inventur.service';
import { IdGeneratorService } from '../shared/command-gateway/id-generator.service';

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
        this.inventur.inventurid$
            .filter(x => x !== '')
            .subscribe(id => this.router.navigate(['inventur', id]))
    }

    inventurBeginnen() {
        this.inventur.beginnen();
    }
}
