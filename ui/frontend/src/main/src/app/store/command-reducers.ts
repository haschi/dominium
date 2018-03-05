import {
    COMMAND_GATEWAY_INITIAL_STATE,
    CommandGatewayState
} from '../shared/command-gateway/command-gateway.model';
import { CommandGatewayActionType } from '../shared/command-gateway/command-gateway-actions.service';

export function commandReducer(state: CommandGatewayState = COMMAND_GATEWAY_INITIAL_STATE, action): CommandGatewayState {
    console.info(`REDUCE COMMAND ${action.type}`);
    switch (action.type) {
        case CommandGatewayActionType.angefordert: {
            return {...state, sendet: true, message: action.message}
        }
        case CommandGatewayActionType.gelungen: {
            return {...state, response: action.response, sendet: false}
        }
        case CommandGatewayActionType.fehlgeschlagen: {
            return {...state, response: action.response, sendet: false}
        }
    }
    return state;
};