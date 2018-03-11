import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ZeileComponent } from './zeile.component';

describe('ZeileComponent', () => {
  let component: ZeileComponent;
  let fixture: ComponentFixture<ZeileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ZeileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ZeileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
