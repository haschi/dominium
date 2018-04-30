import { Query } from './query-decorator';

describe('Ein Feld BeginneHaushaltsbuchführung mit Query Annotation für den Namensraum haschi', () => {
    class X  {
        @Query('haschi')
        static readonly BeginneHaushaltsbuchführung: string = undefined
    }

    it('sollte den Wert "haschi.BeginneHaushaltsbuchführung" haben', () => {
        expect(X.BeginneHaushaltsbuchführung).toBe("haschi.BeginneHaushaltsbuchführung")
    })
})