import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QueryProgressComponent } from './query-progress.component';

describe('QueryProgressComponent', () => {
  let component: QueryProgressComponent;
  let fixture: ComponentFixture<QueryProgressComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QueryProgressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QueryProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
