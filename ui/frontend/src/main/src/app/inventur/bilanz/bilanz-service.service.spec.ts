import { TestBed, inject } from '@angular/core/testing';

import { BilanzServiceService } from './bilanz-service.service';

describe('BilanzServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BilanzServiceService]
    });
  });

  it('should be created', inject([BilanzServiceService], (service: BilanzServiceService) => {
    expect(service).toBeTruthy();
  }));
});
