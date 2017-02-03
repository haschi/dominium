import { Component } from '@angular/core';

import { AppState } from '../app.service';
import { Title } from './title';
import { XLarge } from './x-large';
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  // The selector is what angular internally uses
  // for `document.querySelectorAll(selector)` in our index.html
  // where, in this case, selector is the string 'home'
  selector: 'home',  // <home></home>
  // We need to tell Angular's Dependency Injection which providers are in our app.
  providers: [
    Title
  ],
  // Our list of styles in our component. We may add more to compose many styles together
  styleUrls: [ './home.component.css' ],
  // Every Angular template is first compiled by the browser before Angular runs it's compiler
  templateUrl: './home.component.html'
})
export class HomeComponent {
  // Set our default values
  localState = { value: '' };
  name: string = '';

  form: FormGroup;

  // TypeScript public modifiers
  constructor(public title: Title, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    console.log('hello `Home` component');
    // this.title.getData().subscribe(data => this.data = data);
    this.form = this.formBuilder.group({
      name: ['']
    });

    this.form.valueChanges.subscribe(
        data => {
          console.log("Datenänderung: " + JSON.stringify(data));
          this.name = data.name;
        });

  }

  submitState(): void {
    // console.log('submitState', value);
    // console.log('submitState', value.name);
    // this.name = value.name;
    if (this.form.valid) {
      console.log("submit: " + this.name);
    }
  }
}
