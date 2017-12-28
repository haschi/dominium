import { inject, TestBed } from '@angular/core/testing';

import { CommandGatewayService } from './command-gateway.service';
import { NgReduxModule } from '@angular-redux/store';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StoreModule } from '../../store/store.module';
import { RootEpicsService } from '../../store/root-epics.service';
import { CommandGatewayModule } from './command-gateway.module';
import { CommandType } from '../../inventur/command-type';

describe('CommandGatewayService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [NgReduxModule, HttpClientTestingModule, StoreModule, CommandGatewayModule],
            providers: [RootEpicsService]
        });
    });

    it('should be created', inject([CommandGatewayService], (service: CommandGatewayService) => {
        expect(service).toBeTruthy();
    }));

    it('Aktion angefordert sollte state auf sendet setzen',
        inject([CommandGatewayService], (gateway: CommandGatewayService) => {
            console.info('EXECUTE sollte commando aus epic senden');
            gateway.send(CommandType.BeginneInventur, {id: '12345'}, {});
            gateway.sendet$.subscribe(s => {
                console.info("KONTROLLE: " + s);
                expect(s).toBeTruthy()
            })
        }));

    it('Aktion angefordert sollte message im state setzen',
        inject([CommandGatewayService], (gateway: CommandGatewayService) => {
            gateway.send(CommandType.BeginneInventur, {id: '12345'}, {});

            gateway.message$.subscribe(m => {
                console.info("KONTROLLE: " + JSON.stringify(m));
                expect(m).toEqual({
                    type: CommandType.BeginneInventur,
                    payload: {id: "12345"},
                    meta: {}
                })
            })
        }));

    it('Aktion angefordert sollte Message an Backend senden',
        inject([CommandGatewayService, HttpTestingController],
            (gateway: CommandGatewayService, httpMock: HttpTestingController) => {
                gateway.send(CommandType.BeginneInventur, {id: '12345'}, {});

                const request = httpMock.expectOne('/gateway/command');
                expect(request.request.method).toEqual('POST');
                expect(request.request.body).toEqual({
                    type: CommandType.BeginneInventur,
                    payload: {id: "12345"},
                    meta: {}
                });

                httpMock.verify()
            }));

    it('Aktion angefordert sollte status setzen',
        inject([CommandGatewayService, HttpTestingController],
            (gateway: CommandGatewayService, httpMock: HttpTestingController) => {

                gateway.send(CommandType.BeginneInventur, {id: '12345'}, {});

                const r = httpMock.expectOne('/gateway/command');
                r.flush(null, {status: 202, statusText: 'Accepted'});

                gateway.status$.subscribe(s =>
                    expect(s).toEqual({status: 202, message: 'Accepted'})
                )
            }))
});
