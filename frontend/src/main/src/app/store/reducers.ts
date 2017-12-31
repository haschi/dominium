import { combineReducers, Reducer } from 'redux';
import { AppState, INVENTUR_INITIAL_STATE, InventurState } from './model';
import {
    COMMAND_GATEWAY_INITIAL_STATE, CommandGatewayState, CommandMessage,
    CommandMessageAction
} from '../shared/command-gateway/command-gateway.model';
import { CommandGatewayActionType } from '../shared/command-gateway/command-gateway-actions.service';
import { CommandType } from '../inventur/command-type';
import {
    QUERY_GATEWAY_INITIAL_STATE, QueryAction,
    QueryGatewayState, QueryMessageAction
} from '../shared/query-gateway/query-gateway.model';
import { QueryGatewayActionType } from '../shared/query-gateway/query-gateway-actions.service';
import { QueryType } from '../shared/query-gateway/query-type';

export const inventurReducer: Reducer<InventurState> =
    (state = INVENTUR_INITIAL_STATE, action) => {
        switch (action.type) {
            case CommandGatewayActionType.gelungen: {
                console.info(`REDUCE INVENTAR ${action.type}: ${action.message.type}`);
                switch (action.message.type) {
                    case CommandType.BeginneInventur: {
                        return {...state, inventurId: action.message.payload.id}
                    }
                    // case CommandType.ErfasseInventar: {
                    //     return {...state, inventar: null}
                    // }
                }
            }
            case QueryGatewayActionType.gelungen: {
                console.info(`REDUCE INVENTAR ${action.type}: ${action.message.type}`);
                switch (action.message.type) {
                    case QueryType.LeseInventar: {
                        console.info(`Ergebnis: ${JSON.stringify(action)}`);
                        return {...state, inventar: action.response.body}
                    }
                }
            }
            case QueryGatewayActionType.fehlgeschlagen: {
                console.info(`REDUCE INVENTAR ${action.type}: ${action.message.type}`);
                switch (action.message.type) {
                    case QueryType.LeseInventar: {
                        return {...state, inventar: null}
                    }
                }
            }
        }
        return state;
    };

export const commandReducer: Reducer<CommandGatewayState> =
    (state = COMMAND_GATEWAY_INITIAL_STATE, action) => {
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

export const queryReducer: Reducer<QueryGatewayState> =
    (state = QUERY_GATEWAY_INITIAL_STATE, action) => {
        console.info(`REDUCE QUERY ${action.type}`);
        switch (action.type) {
            case QueryGatewayActionType.angefordert: {
                return {...state, sendet: true, message: action.message}
            }
            case QueryGatewayActionType.gelungen: {
                return {...state, response: action.response, sendet: false}
            }
            case QueryGatewayActionType.fehlgeschlagen: {
                console.error(`REDUCE QUERY fehlgeschlagen: ${JSON.stringify(action)}`);
                return {...state, response: action.response, sendet: false}
            }
        }
        return state;
    };

export const rootReducer = combineReducers<AppState>({
    inventur: inventurReducer,
    command: commandReducer,
    query: queryReducer
});