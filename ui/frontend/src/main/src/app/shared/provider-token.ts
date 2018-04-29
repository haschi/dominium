import { InjectionToken } from '@angular/core';
import { Action } from 'redux';
import { Epic, EpicMiddleware } from 'redux-observable';
import { AppState } from '../store/model';
import { QueryAction } from './query-gateway/query.redux';

export const REDUX_EPIC = new InjectionToken<Epic<Action, {}>>('redux.epic');