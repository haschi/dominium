import { TestBed, inject } from '@angular/core/testing';

import { QueryGatewayService } from './query-gateway.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LoggerService } from '../logger.service';

describe('QueryGatewayService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      providers: [QueryGatewayService, LoggerService]
    });
  });

  it('should be created', inject([QueryGatewayService], (service: QueryGatewayService) => {
    expect(service).toBeTruthy();
  }));
});
