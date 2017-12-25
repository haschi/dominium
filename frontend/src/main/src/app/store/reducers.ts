import { combineReducers, Reducer } from 'redux';
import { AppState, INVENTUR_INITIAL_STATE, InventurState } from './model';
import { Actions } from './actions.service';

export const inventurReducer: Reducer<InventurState> =
    (state = INVENTUR_INITIAL_STATE, action) => {
        console.info("inventur reducer: " + JSON.stringify(action));
        switch (action.type) {
            case Actions.InventurBegonnen: {
                console.info("REDUCE Inventur begonnen");
                return {...state, inventurId: action.id}
            }
        }
        return state;
    };

export const rootReducer = combineReducers<AppState>({
    inventur: inventurReducer
});