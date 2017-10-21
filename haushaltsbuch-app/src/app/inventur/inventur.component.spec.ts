import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventurComponent } from './inventur.component';
import { AppMaterialModule } from '../app-material-module';
import { AppCovalentModuleModule } from '../app-covalent-module.module';
import { DEMO_APP_ROUTES } from '../routes';
import { RouterTestingModule } from '@angular/router/testing';
import { HomeComponent } from '../home/home.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('InventurComponent', () => {
    let component: InventurComponent;
    let fixture: ComponentFixture<InventurComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [InventurComponent, HomeComponent],
            imports: [
                NoopAnimationsModule,
                AppMaterialModule,
                AppCovalentModuleModule,
                RouterTestingModule.withRoutes(DEMO_APP_ROUTES)
            ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(InventurComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
