import { AppState } from './model';
import { combineReducers, Reducer } from 'redux';
import { inventurReducer } from './inventur-reducers';
import { commandReducer } from './command-reducers';
import { queryReducer } from './query-reducers';


export const rootReducer = combineReducers<AppState>({
    inventur: inventurReducer,
    command: commandReducer,
    query: queryReducer
});
