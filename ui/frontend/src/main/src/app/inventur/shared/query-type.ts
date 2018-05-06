import { Query } from '../../shared/query-gateway/query-decorator';

export class QueryType {
    static readonly KeinQuery = '';
    static readonly LeseInventar = 'com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar';
    static readonly LeseEroeffnungsbilanz = 'com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseEröffnungsbilanz';
    static readonly LeseInventurGruppen = 'com.github.haschi.dominium.haushaltsbuch.core.domain.inventur.LeseInventurGruppen'
}
