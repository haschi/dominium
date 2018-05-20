import { inventurGruppen } from '../inventur/shared/gruppen.redux';
import { inventureingabe } from '../inventur/shared/inventar-eingabe.redux';
import { command } from '../shared/command-gateway/command.redux';
import { AppState } from './model';
import { combineReducers } from 'redux';
import { inventur } from '../inventur/shared/inventur.redux';
import { query } from '../shared/query-gateway/query.redux';

export const rootReducer = combineReducers<AppState>({
    inventur,
    inventurGruppen,
    command,
    query,
    inventureingabe
});
