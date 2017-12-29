import { TestBed, inject } from '@angular/core/testing';

import { QueryGatewayService } from './query-gateway.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { QueryGatewayActionsService } from './query-gateway-actions.service';

describe('QueryGatewayService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      providers: [QueryGatewayService, QueryGatewayActionsService]
    });
  });

  it('should be created', inject([QueryGatewayService], (service: QueryGatewayService) => {
    expect(service).toBeTruthy();
  }));
});
