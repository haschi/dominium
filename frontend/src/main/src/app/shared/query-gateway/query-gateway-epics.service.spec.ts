import { TestBed, inject } from '@angular/core/testing';

import { QueryGatewayEpicsService } from './query-gateway-epics.service';

describe('QueryGatewayEpicsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [QueryGatewayEpicsService]
    });
  });

  it('should be created', inject([QueryGatewayEpicsService], (service: QueryGatewayEpicsService) => {
    expect(service).toBeTruthy();
  }));
});
