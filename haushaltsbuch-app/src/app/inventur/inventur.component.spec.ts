import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventurComponent } from './inventur.component';

describe('InventurComponent', () => {
  let component: InventurComponent;
  let fixture: ComponentFixture<InventurComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InventurComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
