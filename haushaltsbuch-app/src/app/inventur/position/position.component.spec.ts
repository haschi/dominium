import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PositionComponent } from './position.component';
import { DEMO_APP_ROUTES } from '../../routes';
import { RouterTestingModule } from '@angular/router/testing';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AppCovalentModuleModule } from '../../shared/app-covalent-module.module';
import { AppMaterialModule } from '../../shared/app-material-module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Component, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA, OnInit } from '@angular/core';

describe('PositionComponent', () => {
  let component: TestHostComponent;
  let fixture: ComponentFixture<TestHostComponent>;

  @Component({
      template: `
          <form [formGroup]="formGroup">
          <app-position [parent]="formGroup" [arrayName]="'schulden'"></app-position>
          </form>
      `
  })
    class TestHostComponent implements OnInit{

      formGroup: FormGroup;

      constructor(private builder: FormBuilder) {
      }
      ngOnInit() {
          this.formGroup = this.builder.group({
              schulden: this.builder.array([])
          });
      }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PositionComponent, TestHostComponent],
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
