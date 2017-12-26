import { async, inject, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { By } from '@angular/platform-browser';
import { TdLayoutComponent } from '@covalent/core';
import { AppModule } from './app.module';
import { APP_BASE_HREF } from '@angular/common';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RootEpicsService } from './store/root-epics.service';
import { CommandBusEpicsService } from './shared/command-bus-epics.service';
import { CommandBusActionsService } from './shared/command-bus-actions.service';
import { CommandBusService } from './shared/command-bus.service';

describe('AppComponent', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule({

            imports: [AppModule, HttpClientTestingModule],
            providers: [RootEpicsService, CommandBusEpicsService, CommandBusActionsService, CommandBusService,
                {provide: APP_BASE_HREF, useValue: '/'}]
        }).compileComponents();
    }));

    it('should create the app', async(() => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    }));

    it('sollte covalent layout besitzen', async(() => {
        const fixture = TestBed.createComponent(AppComponent);
        const de = fixture.debugElement;
        expect(de.query(By.directive(TdLayoutComponent))).toBeTruthy();
    }));

    it(`sollte eine route für home haben`, async(() => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app.routes).toContain({title: 'Home', route: '', icon: 'home'});
    }));

    it('should render title in a h1 tag',

            inject([HttpTestingController], (backend: HttpTestingController) => {
            const fixture = TestBed.createComponent(AppComponent);

            fixture.detectChanges();
                backend.expectOne({url: '/gateway/version', method: 'GET'})
                    .flush("Version 1");
            const compiled = fixture.debugElement.nativeElement;
            expect(compiled.querySelector('span').textContent).toContain('Haushaltsbuch');
    }));
});
