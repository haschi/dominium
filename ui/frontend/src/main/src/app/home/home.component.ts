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

    constructor(private idGenerator: IdGeneratorService,
                private inventur: InventurService,
                private router: Router) {
    }

    ngOnInit() {
    }

    inventurBeginnen() {
        let id = this.idGenerator.neu();
        this.inventur.beginneInventur(id);

        this.inventur.inventurid$
            .filter(x => x == id)
            .subscribe(
                x => this.router.navigate(['inventur', id]),
                err => console.error('ERROR: ' + err)
            )
    }
}
