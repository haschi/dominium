import { ComponentFixture, inject, TestBed } from '@angular/core/testing';

import { CommandGatewayService } from './command-gateway.service';
import { NgReduxModule } from '@angular-redux/store';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StoreModule } from '../../store/store.module';
import { CommandGatewayModule } from './command-gateway.module';
import { CommandType } from '../../inventur/command-type';
import { AppComponent } from '../../app.component';
import { AppModule } from '../../app.module';
import { APP_BASE_HREF } from '@angular/common';
import { InventurComponent } from '../../inventur/inventur.component';
import { By } from '@angular/platform-browser';
import {
    TdConfirmDialogComponent, TdDialogComponent,
    TdDialogTitleDirective
} from '@covalent/core';

describe('CommandGatewayService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                AppModule,
                NgReduxModule,
                HttpClientTestingModule,
                StoreModule,
                CommandGatewayModule
            ],
            providers: [
                {provide: APP_BASE_HREF, useValue: '/'}
            ]
        });
    });

    let component: AppComponent;
    let fixture: ComponentFixture<AppComponent>;

    beforeEach(() => {
        // fixture = TestBed.createComponent(AppComponent);
        // component = fixture.componentInstance;
        // fixture.detectChanges();
    });

    it('should be created', inject([CommandGatewayService], (service: CommandGatewayService) => {
        expect(service).toBeTruthy();
    }));

    describe('Gateway sendet ein Command', () => {
        beforeEach(inject([CommandGatewayService], (gateway: CommandGatewayService) => {
            gateway.send(CommandType.BeginneInventur, {id: '12345'}, {});
        }));

        it('Aktion "angefordert" sollte state auf sendet setzen',
            inject([CommandGatewayService], (gateway: CommandGatewayService) => {
                gateway.sendet$.subscribe(s => {
                    expect(s).toBeTruthy()
                })
            }));

        it('Aktion "angefordert" sollte message im state setzen',
            inject([CommandGatewayService], (gateway: CommandGatewayService) => {
                gateway.message$.subscribe(m => {
                    expect(m).toEqual({
                        type: CommandType.BeginneInventur,
                        payload: {id: "12345"},
                        meta: {}
                    })
                })
            }));

        it('Aktion "angefordert" sollte Message an Backend senden',
            inject([CommandGatewayService, HttpTestingController],
                (gateway: CommandGatewayService, httpMock: HttpTestingController) => {
                    const request = httpMock.expectOne('/gateway/command');
                    expect(request.request.method).toEqual('POST');
                    expect(request.request.body).toEqual({
                        type: CommandType.BeginneInventur,
                        payload: {id: "12345"},
                        meta: {}
                    });

                    httpMock.verify()
                }));

        it('Aktion "angefordert" sollte status setzen',
            inject([CommandGatewayService, HttpTestingController],
                (gateway: CommandGatewayService, httpMock: HttpTestingController) => {
                    const request = httpMock.expectOne('/gateway/command');
                    request.flush(null, {status: 202, statusText: 'Accepted'});

                    gateway.status$.subscribe(s =>
                        expect(s).toEqual({status: 202, message: 'Accepted'})
                    )
                }));

        it('Aktion "angefordert" sollte Dialog öffnen, falls Response kaputt',
            inject([CommandGatewayService, HttpTestingController],
                (gateway: CommandGatewayService, httpMock: HttpTestingController) => {
                    const request = httpMock.expectOne('/gateway/command');
                    request.flush(null, {status: 504, statusText: 'Gateway Timeout'});

                    // fixture.detectChanges();

                    // let ne = fixture.nativeElement;
                    // let dialog = fixture.debugElement.query(By.css('.cdk-overlay-container'));
                    // let dialog1 = fixture.debugElement.query(By.directive(TdConfirmDialogComponent));
                    // expect(dialog).toBeTruthy();
                }));
    });
});
