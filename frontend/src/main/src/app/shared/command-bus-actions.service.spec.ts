import { inject, TestBed } from '@angular/core/testing';

import { CommandBusActionsService } from './command-bus-actions.service';

describe('CommandBusActionsService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [CommandBusActionsService]
        });
    });

    it('should be created', inject([CommandBusActionsService], (service: CommandBusActionsService) => {
        expect(service).toBeTruthy();
    }));
});
