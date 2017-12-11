import { async, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { By } from '@angular/platform-browser';
import { TdLayoutComponent } from '@covalent/core';
import { AppModule } from './app.module';
import { APP_BASE_HREF } from '@angular/common';

describe('AppComponent', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule({

            imports: [AppModule],
            providers: [
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

    it('should render title in a h1 tag', async(() => {
        const fixture = TestBed.createComponent(AppComponent);
        fixture.detectChanges();
        const compiled = fixture.debugElement.nativeElement;
        expect(compiled.querySelector('span').textContent).toContain('Haushaltsbuch');
    }));
});
