import { TestBed, inject } from '@angular/core/testing';

import { CommandBusService } from './command-bus.service';

describe('CommandBusService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CommandBusService]
    });
  });

  it('should be created', inject([CommandBusService], (service: CommandBusService) => {
    expect(service).toBeTruthy();
  }));
});
