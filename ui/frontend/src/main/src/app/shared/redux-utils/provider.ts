import { InjectionToken, Provider } from '@angular/core';
import { Action } from 'redux';
import { Epic } from 'redux-observable';

export const REDUX_EPIC = new InjectionToken<Epic<Action, {}>>('redux.epic');

export function EpicProvider(
    epic: (...args: any[]) => Epic<Action, {}>,
    dependencies: any[] = [])
    : Provider {
    return {
        provide: REDUX_EPIC,
        multi: true,
        deps: dependencies,
        useFactory: epic
    }
}