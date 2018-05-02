import { HttpClient } from '@angular/common/http';
import { async, inject, TestBed } from '@angular/core/testing';
import { Action } from 'redux';
import { Epic } from 'redux-observable';
import { AppState } from '../../store/model';
import { REDUX_EPIC } from '../redux-utils/provider';
import { EpicProvider } from '../redux-utils/provider';
import {
    fallsQueryAngefordert,
    query,
    QUERY_GATEWAY_INITIAL_STATE,
    QueryAction,
    queryAngefordert,
    queryFehlgeschlagen,
    QueryGatewayActionType,
    queryGelungen
} from './query.redux';

describe('Query Epic Provider', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            providers: [
                EpicProvider(fallsQueryAngefordert, [HttpClient]),
                {provide: HttpClient, useValue: {}},
            ]
        }).compileComponents();
    }));

    it('sollte injizierbar sein', inject([REDUX_EPIC], (epic: Epic<Action, {}>[]) => {
        expect(epic).toBeTruthy()
    }))
})

describe('Query Angefordert Action Creator', () => {

    const action = queryAngefordert('QueryLesen', {id: '1234'}, 'ErgebnisTyp')

    it('sollte Action liefern', () => {
        expect(action).toEqual({
            type: QueryGatewayActionType.angefordert,
            message: {
                type: 'QueryLesen',
                payload: {id: '1234'},
                result: 'ErgebnisTyp'
            }
        })
    })

    it('sollte vom Reducer verarbeitet werden', () => {
        const state = query(QUERY_GATEWAY_INITIAL_STATE, action);
        expect(state).toEqual({
            message: action.message,
            response: QUERY_GATEWAY_INITIAL_STATE.response,
            sendet: true
        })
    })
})

describe('Query Gelungen Action Creator', () => {
    const action = queryGelungen(
        {type: 'QueryLesen', payload: {id: '1234'}, result: 'ErgebnisTyp'},
        {status: 200, message: 'OK', body: {content: 'Inhalt ist da'}})

    it ('sollte Action liefern', () => {
        expect(action).toEqual({
            type: QueryGatewayActionType.gelungen,
            message: {type: 'QueryLesen', payload: {id: '1234'}, result: 'ErgebnisTyp'},
            response: {status: 200, message: 'OK', body: {content: 'Inhalt ist da'}}
        })
    })

    it('sollte vom Reducer verarbeitet werden', () => {
        const state = query(QUERY_GATEWAY_INITIAL_STATE, action);
        expect(state).toEqual({
            message: QUERY_GATEWAY_INITIAL_STATE.message,
            response: {status: 200, message: 'OK', body: {content: 'Inhalt ist da'}},
            sendet: false
        })
    })
})

describe('Query Fehlgeschlagen Action Creator', () => {

    const action = queryFehlgeschlagen(
        {type: 'QueryLesen', payload: {id: '1234'}, result: 'ErgebnisTyp'},
        500, "Fehlermeldung");

    it ('sollte Action liefern', () => {
        expect(action).toEqual({
            type: QueryGatewayActionType.fehlgeschlagen,
            message: {type: 'QueryLesen', payload: {id: '1234'}, result: 'ErgebnisTyp'},
            response: {status: 500, message: 'Fehlermeldung', body: {}}
        })
    })

    it('sollte vom Reducer verarbeitet werden', () => {
        const state = query(QUERY_GATEWAY_INITIAL_STATE, action);
        expect(state).toEqual({
            message: QUERY_GATEWAY_INITIAL_STATE.message,
            response: {status: 500, message: 'Fehlermeldung', body: {}},
            sendet: false
        })
    })
})
