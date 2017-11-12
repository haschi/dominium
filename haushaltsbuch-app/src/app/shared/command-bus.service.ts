import 'rxjs/add/observable/bindNodeCallback';
import 'rxjs/add/observable/defer';
import 'rxjs/add/observable/empty';
import 'rxjs/add/observable/fromEvent';
import 'rxjs/add/observable/fromEventPattern';
import 'rxjs/add/observable/merge';
import 'rxjs/add/operator/first';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mapTo';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/takeUntil';

import { Injectable } from '@angular/core';
import * as EventBus from 'vertx3-eventbus-client';
import { Observable } from 'rxjs/Observable';
import { State } from './state';
import { Message } from './message';
import { LoggerService } from './logger.service';

@Injectable()
export class CommandBusService {

    private delegate: any;

    state$: Observable<State>;

    private _closeEvent: CloseEvent | null = null;

  constructor(private log: LoggerService) {

      // Alternatively, pass in an options object
      const options = {
          vertxbus_reconnect_attempts_max: Infinity, // Max reconnect attempts
          vertxbus_reconnect_delay_min: 1000, // Initial delay (in ms) before first reconnect attempt
          vertxbus_reconnect_delay_max: 5000, // Max delay (in ms) between reconnect attempts
          vertxbus_reconnect_exponent: 2, // Exponential backoff factor
          vertxbus_randomization_factor: 0.5, // Randomization factor between 0 and 1
          vertxbus_ping_interval: 30000
      };
      this.delegate = new EventBus('/eventbus', options);
      this.delegate.enableReconnect(true);

      // this.delegate.pingEnabled(false);
      this.delegate.enablePing(false);

      // capture close event
      this._stateClosedEvent$.subscribe(
          event => this._closeEvent = event || null,
      );
      // init state$
      this.state$ = Observable.defer(() => {
          return Observable
              .merge(
                  this._stateOpenEvent$.mapTo(State.OPEN).takeUntil(this._stateClosedEvent$),
                  this._stateClosedEvent$.mapTo(State.CLOSED),
              )
              .startWith(this.delegate.state);
      });

      this.log.log('Command Bus erzeugt');
  }

    send(address: string, message: any, headers?: any): Observable<Message<any>> {
      this.log.log('Sending Command');

        const generatorFn =
            Observable.bindNodeCallback<string, any, (any | undefined), Message<any>>(
                this.delegate.send.bind(this.delegate));

        return generatorFn(address, message, headers)
            .map(this._appendReplyFns)
            .takeUntil(this._stateClosedEvent$);
    }

    private get _stateOpenEvent$() {
        if (this.state !== State.CONNECTING) {
            return Observable.empty<void>();
        }
        return Observable
            .fromEvent<void>(this.delegate.sockJSConn, 'open')
            .first();
    }

    get state(): State {
        return this.delegate.state;
    }

    get closeEvent(): CloseEvent | null {
        return this._closeEvent;
    }

    private get _stateClosedEvent$() {
        if (this.state === State.CLOSED) {
            return Observable.empty<CloseEvent>();
        }
        return Observable
            .fromEvent<CloseEvent>(this.delegate.sockJSConn, 'close')
            .first();
    }

    private _appendReplyFns = <T>(msg: Message<T>): Message<T> => {
        const replyAddress = msg.replyAddress;
        if (!replyAddress) {
            return msg;
        }
        return {
            ...msg,
            rxReply: (message: any, headers?: any) => {
                return this.send(replyAddress, message, headers);
            }
        };
    }
}
