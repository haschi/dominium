import { HttpClient } from '@angular/common/http';
import { async, inject, TestBed } from '@angular/core/testing';
import { Action } from 'redux';
import { Epic } from 'redux-observable';
import { REDUX_EPIC } from '../provider-token';
import {
    queryAngefordert,
    QueryEpicProvider,
    QueryGatewayActionType,
    queryGelungen
} from './query.redux';

describe('Query Epic Provider', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            providers: [
                QueryEpicProvider,
                {provide: HttpClient, useValue: {}},
            ]
        }).compileComponents();
    }));

    it('sollte injizierbar sein', inject([REDUX_EPIC], (epic: Epic<Action, {}>[]) => {
        expect(epic).toBeTruthy()
    }))
})

describe('Query Angefordert Action Creator', () => {
    it('sollte Action liefern', () => {
        const action = queryAngefordert('QueryLesen', {id: '1234'}, 'ErgebnisTyp')
        expect(action).toEqual({
            type: QueryGatewayActionType.angefordert,
            message: {
                type: 'QueryLesen',
                payload: {id: '1234'},
                result: 'ErgebnisTyp'
            }
        })
    })
})

describe('Query Gelungen Action Creator', () => {
    it ('sollte Action liefern', () => {
        const action = queryGelungen(
            {type: 'QueryLesen', payload: {id: '1234'}, result: 'ErgebnisTyp'},
            {status: 200, message: 'OK', body: {content: 'Inhalt ist da'}})

        expect(action).toEqual({
            type: QueryGatewayActionType.gelungen,
            message: {type: 'QueryLesen', payload: {id: '1234'}, result: 'ErgebnisTyp'},
            response: {status: 200, message: 'OK', body: {content: 'Inhalt ist da'}}
        })
    })
})