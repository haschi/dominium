import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ErfassungComponent } from './erfassung.component';

describe('ErfassungComponent', () => {
  let component: ErfassungComponent;
  let fixture: ComponentFixture<ErfassungComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ErfassungComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ErfassungComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
