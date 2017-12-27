import { combineReducers, Reducer } from 'redux';
import { AppState, INVENTUR_INITIAL_STATE, InventurState } from './model';
import {
    COMMAND_GATEWAY_INITIAL_STATE, CommandGatewayState,
    CommandMessageAction
} from '../shared/command-gateway/command-gateway.model';
import { CommandGatewayActionType } from '../shared/command-gateway/command-gateway-actions.service';
import { Actions } from './actions';

export const inventurReducer: Reducer<InventurState> =
    (state = INVENTUR_INITIAL_STATE, action: CommandMessageAction) => {
        console.info("inventur reducer: " + JSON.stringify(action));
        switch (action.type) {
            case Actions.InventurBegonnen: {
                console.info("REDUCE Inventur begonnen");
                return {...state, inventurId: action.message.payload.id}
            }
        }
        return state;
    };

export const commandReducer: Reducer<CommandGatewayState> =
    (state = COMMAND_GATEWAY_INITIAL_STATE, action) => {
        switch (action.type) {
            case CommandGatewayActionType.angefordert: {
                console.info("REDUCE COMMAND angefordert");
                return {...state, sendet: true, message: action.message}
            }
            case CommandGatewayActionType.gelungen: {
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