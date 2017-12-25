import { inject, TestBed } from '@angular/core/testing';

import { ActionsService } from './actions.service';

describe('ActionsService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [ActionsService]
        });
    });

    it('should be created', inject([ActionsService], (service: ActionsService) => {
        expect(service).toBeTruthy();
    }));

    it('sollte begonnen Action liefern', inject([ActionsService], (service: ActionsService) => {
        console.info("sollte begonnen Action liefern: " + JSON.stringify(service.begonnen("12345")));
        expect(service.begonnen("12345"))
            .toEqual({type: ActionsService.InventurBegonnen, id: "12345"})
    }));

    it('sollte erfasst Action liefern', inject([ActionsService], (service: ActionsService) => {
        let inventar = {
            anlagevermoegen: [],
            umlaufvermoegen: [],
            schulden: []
        };
        expect(service.erfasst(inventar)).toEqual({type: ActionsService.InventarErfasst, inventar})
    }))

});
