import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QueryErrorComponent } from './query-error.component';
import { QueryGatewayModule } from '../query-gateway.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LoggerService } from '../../logger.service';
import { StoreModule } from '../../../store/store.module';
import { CommandGatewayModule } from '../../command-gateway/command-gateway.module';

describe('QueryErrorComponent', () => {
  let component: QueryErrorComponent;
  let fixture: ComponentFixture<QueryErrorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        providers: [LoggerService],
        imports: [
            QueryGatewayModule,
            CommandGatewayModule,
            HttpClientTestingModule,
            StoreModule
        ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QueryErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
