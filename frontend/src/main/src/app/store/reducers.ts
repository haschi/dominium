import { combineReducers, Reducer } from 'redux';
import { AppState, INVENTUR_INITIAL_STATE, InventurState } from './model';
import {
    COMMAND_GATEWAY_INITIAL_STATE, CommandGatewayState,
    CommandMessageAction
} from '../shared/command-gateway/command-gateway.model';
import { CommandGatewayActionType } from '../shared/command-gateway/command-gateway-actions.service';
import { CommandType } from '../inventur/command-type';

export const inventurReducer: Reducer<InventurState> =
    (state = INVENTUR_INITIAL_STATE, action: CommandMessageAction) => {
        switch (action.type) {
            case CommandGatewayActionType.gelungen: {
                switch (action.message.type) {
                    case CommandType.BeginneInventur: {
                        console.info("REDUCE Inventur begonnen: " + JSON.stringify(action));
                        return {...state, inventurId: action.message.payload.id}
                    }
                    case CommandType.ErfasseInventar: {
                        console.info('REDUCE Inventar erfasst');
                        return {...state, inventar: action.message.payload.inventar}
                    }
                }
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
            case CommandGatewayActionType.fehlgeschlagen: {
                console.info("REDUCE COMMAND fehlgeschlagen " + JSON.stringify(action));
                return {...state, response: action.response, sendet: false,}
            }
        }
        return state;
    };

export const rootReducer = combineReducers<AppState>({
    inventur: inventurReducer,
    command: commandReducer
});