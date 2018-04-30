import { command } from '../shared/command-gateway/command.redux';
import { AppState } from './model';
import { combineReducers } from 'redux';
import { inventurReducer } from './inventur-reducers';
import { query } from '../shared/query-gateway/query.redux';


export const rootReducer = combineReducers<AppState>({
    inventur: inventurReducer,
    command: command,
    query: query
});
