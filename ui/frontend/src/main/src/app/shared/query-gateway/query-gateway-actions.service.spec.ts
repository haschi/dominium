import { TestBed, inject } from '@angular/core/testing';

import { QueryGatewayActionsService } from './query-gateway-actions.service';

describe('QueryGatewayActionsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [QueryGatewayActionsService]
    });
  });

  it('should be created', inject([QueryGatewayActionsService], (service: QueryGatewayActionsService) => {
    expect(service).toBeTruthy();
  }));
});
