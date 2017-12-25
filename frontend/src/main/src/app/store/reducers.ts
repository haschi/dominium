import { combineReducers, Reducer } from 'redux';
import { AppState, INVENTUR_INITIAL_STATE, InventurState } from './model';

const inventurReducer: Reducer<InventurState> =
    (state = INVENTUR_INITIAL_STATE, action) => {
        return state;
    };

export const rootReducer = combineReducers<AppState>({
    inventur: inventurReducer
});