import { Command } from './query-decorator';

describe('Ein Feld BeginneHaushaltsbuchführung mit Query Annotation für den Namensraum haschi', () => {
    class X  {
        @Command('haschi')
        static readonly BeginneHaushaltsbuchführung: string = undefined
    }

    it('sollte den Wert "haschi.BeginneHaushaltsbuchführung" haben', () => {
        expect(X.BeginneHaushaltsbuchführung).toBe("haschi.BeginneHaushaltsbuchführung")
    })
})