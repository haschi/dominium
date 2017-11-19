import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GruppeComponent } from './gruppe.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AppCovalentModuleModule } from '../../shared/app-covalent-module.module';
import { AppMaterialModule } from '../../shared/app-material-module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Component, OnInit } from '@angular/core';
import { PostenComponent } from './posten/posten.component';

describe('GruppeComponent', () => {
    let component: TestHostComponent;
    let fixture: ComponentFixture<TestHostComponent>;

    @Component({
        template: `
            <form [formGroup]="formGroup">
                <app-inventur-gruppe [positionen]="formArray">
                    <h3>Schulden</h3>
                </app-inventur-gruppe>
            </form>
        `
    })
    class TestHostComponent implements OnInit {

        formGroup: FormGroup;
        formArray: FormArray;

        constructor(private builder: FormBuilder) {
        }

        ngOnInit() {
            this.formArray =
                this.builder.array([]);

            this.formGroup = this.builder.group({
                positionen: this.formArray
            });
        }
    }

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [GruppeComponent, PostenComponent, TestHostComponent],
            imports: [
                NoopAnimationsModule,
                AppMaterialModule,
                AppCovalentModuleModule,
                ReactiveFormsModule,
                CurrencyMaskModule,
            ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(TestHostComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
