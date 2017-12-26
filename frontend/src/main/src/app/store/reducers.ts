import { combineReducers, Reducer } from 'redux';
import { AppState, INVENTUR_INITIAL_STATE, InventurState } from './model';
import { Actions } from './actions.service';
import {
    COMMAND_BUS_INITIAL_STATE,
    CommandBusState
} from '../shared/command-gateway/command-bus.model';
import { CommandBusActions } from '../shared/command-gateway/command-bus-actions.service';

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

export const commandReducer: Reducer<CommandBusState> =
    (state = COMMAND_BUS_INITIAL_STATE, action) => {
        switch (action.type) {
            case CommandBusActions.angefordert: {
                console.info("REDUCE COMMAND angefordert");
                return {...state, sendet: true, message: action.message}
            }
            case CommandBusActions.gelungen: {
                console.info("REDUCE COMMAND gelungen " + JSON.stringify(action));
                return {...state, response: action.response, sendet: false}
            }
        }
        return state;
    };

export const rootReducer = combineReducers<AppState>({
    inventur: inventurReducer,
    command: commandReducer
});