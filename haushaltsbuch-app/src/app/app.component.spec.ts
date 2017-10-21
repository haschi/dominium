import { async, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { RouterTestingModule } from '@angular/router/testing';
import { ALL_ROUTES } from './routes';
import { AppMaterialModule } from './app-material-module';
import { HomeComponent } from './home/home.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { InventurComponent } from './inventur/inventur.component';
import { AppCovalentModuleModule } from './app-covalent-module.module';
import { By } from '@angular/platform-browser';
import { TdLayoutCardOverComponent, TdLayoutComponent, TdLayoutNavComponent } from '@covalent/core';

describe('AppComponent', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [
                AppComponent, HomeComponent, InventurComponent
            ],
            imports: [
                AppMaterialModule,
                AppCovalentModuleModule,
                NoopAnimationsModule,
                RouterTestingModule.withRoutes(ALL_ROUTES)]
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
        expect(app.routes).toContain({title: 'Home', route: '/', icon: 'home'});
    }));

    it('should render title in a h1 tag', async(() => {
        const fixture = TestBed.createComponent(AppComponent);
        fixture.detectChanges();
        const compiled = fixture.debugElement.nativeElement;
        expect(compiled.querySelector('span').textContent).toContain('Haushaltsbuch');
    }));
});
