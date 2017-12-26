import { Action } from 'redux';

export interface CommandBusState {
    message: CommandMessage
    response: CommandResponse
    sendet: boolean
}

export interface CommandMessage {
    type: string,
    payload: any,
    meta: any,
}

export interface CommandResponse {
    status: number,
    message: string
}

export type CommandMessageAction = Action & { message: CommandMessage }
export type CommandResponseAction = Action & { response: CommandResponse }
export type CommandAction = CommandMessageAction | CommandResponseAction

export const COMMAND_BUS_INITIAL_STATE: CommandBusState = {
    sendet: false,
    message: {type: '', payload: {}, meta: {}},
    response: {status: 0, message: ''}
};