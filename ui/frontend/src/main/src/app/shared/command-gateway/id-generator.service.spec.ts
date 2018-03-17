import { inject, TestBed } from '@angular/core/testing';

import { IdGeneratorService } from './id-generator.service';

describe('IdGeneratorService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [IdGeneratorService]
        });
    });

    it('should be created', inject([IdGeneratorService], (service: IdGeneratorService) => {
        expect(service).toBeTruthy();
    }));

    it('sollte uuid generieren', inject([IdGeneratorService], (service: IdGeneratorService) => {
        expect(service.neu()).toMatch(/[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}/)
    }))

    it('sollte bei jedem Aufruf verschiedene uuid generieren',
        inject([IdGeneratorService], (service: IdGeneratorService) => {
            expect(service.neu()).not.toBe(service.neu());
    }))
});
