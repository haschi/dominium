import { Action } from 'redux';
import { CommandType } from '../../inventur/command-type';

export interface CommandGatewayState {
    message: CommandMessage
    response: CommandResponse
    sendet: boolean
}

export interface CommandMessage {
    type: CommandType,
    payload: any,
    meta: any,
}

export interface CommandResponse {
    status: number,
    message: string
}

export type CommandMessageAction = Action & { message: CommandMessage }
export type CommandResponseAction = CommandMessageAction & { response: CommandResponse }
export type CommandAction = CommandMessageAction | CommandResponseAction

export const COMMAND_GATEWAY_INITIAL_STATE: CommandGatewayState = {
    sendet: false,
    message: {type: CommandType.KeinCommand, payload: {}, meta: {}},
    response: {status: 0, message: ''}
};
