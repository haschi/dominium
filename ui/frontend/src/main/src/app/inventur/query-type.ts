import { Query } from '../shared/query-gateway/query-decorator';

// const haushaltsbuch = 'com.github.haschi.dominium.haushaltsbuch.core.model.queries';

export class QueryType {
    static readonly KeinQuery = '';

    // @Query(haushaltsbuch)
    static readonly LeseInventar = 'com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseInventar';

    // @Query(haushaltsbuch)
    static readonly LeseEroeffnungsbilanz = 'com.github.haschi.dominium.haushaltsbuch.core.model.queries.LeseEröffnungsbilanz';
}
