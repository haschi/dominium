import { TestBed, inject } from '@angular/core/testing';

import { QueryGatewayEpicsService } from './query-gateway-epics.service';
import { QueryGatewayModule } from './query-gateway.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LoggerService } from '../logger.service';

describe('QueryGatewayEpicsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        providers: [LoggerService],
      imports: [QueryGatewayModule, HttpClientTestingModule]
    });
  });

  it('should be created', inject([QueryGatewayEpicsService], (service: QueryGatewayEpicsService) => {
    expect(service).toBeTruthy();
  }));
});
