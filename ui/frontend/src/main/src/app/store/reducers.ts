import { commandReducer } from '../shared/command-gateway/command-reducers';
import { AppState } from './model';
import { combineReducers } from 'redux';
import { inventurReducer } from './inventur-reducers';
import { queryReducer } from '../shared/query-gateway/query-reducers';


export const rootReducer = combineReducers<AppState>({
    inventur: inventurReducer,
    command: commandReducer,
    query: queryReducer
});
