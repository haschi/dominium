import { CommandGatewayActionType } from './command-gateway-actions.service';
import { COMMAND_GATEWAY_INITIAL_STATE, CommandGatewayState } from './command-gateway.model';

export function commandReducer(state: CommandGatewayState = COMMAND_GATEWAY_INITIAL_STATE, action): CommandGatewayState {
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
}