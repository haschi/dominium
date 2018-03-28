import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BilanzComponent } from './bilanz.component';

describe('BilanzComponent', () => {
  let component: BilanzComponent;
  let fixture: ComponentFixture<BilanzComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BilanzComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BilanzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
