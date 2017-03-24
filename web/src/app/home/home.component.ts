import { Component, OnInit } from '@angular/core';
import { Title } from './title';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Aktionen } from '../Aktionen';
import { select } from '@angular-redux/store';
import { AppState, HaushaltsbuchState } from '../reducer';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';

@Component({
    // The selector is what angular internally uses
    // for `document.querySelectorAll(selector)` in our index.html
    // where, in this case, selector is the string 'home'
    selector: 'domimium-home',  // <home></home>
    // We need to tell Angular's Dependency Injection which providers are in our app.
    providers: [
        Title
    ],
    // Our list of styles in our component. We may add more to compose many styles together
    styleUrls: ['./home.component.css'],
    // Every Angular template is first compiled by the browser before Angular runs it's compiler
    templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
    // Set our default values
    localState = {value: ''};
    name = '';

    form: FormGroup;

    @select((s: AppState) => s.haushaltsbuch)
    id$: Observable<HaushaltsbuchState>;

    // TypeScript public modifiers
    constructor(public title: Title,
                private formBuilder: FormBuilder,
                private aktion: Aktionen,
                private http: Http,
                private router: Router) {
    }

    ngOnInit() {
        console.log('hello `Home` component');
        // this.title.getData().subscribe(data => this.data = data);
        this.form = this.formBuilder.group({
            name: ['']
        });

        this.form.valueChanges.subscribe(
            data => {
                console.log('Datenänderung: ' + JSON.stringify(data));
                this.name = data.name;
            });

        this.id$.subscribe((data: HaushaltsbuchState) => {
            console.log('Datenänderung Haushaltsbuch:' + JSON.stringify(data));
            if (data.location != null) {
                console.log('Prozessstatus gefunden bei: ' + data.location);
                this.http.get(data.location).subscribe(
                    (r: Response) => {
                        console.log(r.text());
                    },
                    (error) => {
                        console.log(error);
                    });
            }
            if (data.location === 'hellow rold') {
                this.router.navigate(['/dashboard', data.id]);
            }
        });
    }

    submitState(): void {
        if (this.form.valid) {
            console.log('submit: ' + this.name);
            this.aktion.legeHaushaltsbuchAn(this.name);
        }
    }
}
