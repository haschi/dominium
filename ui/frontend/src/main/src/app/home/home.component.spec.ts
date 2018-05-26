import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { APP_INITIAL_STATE, AppState } from '../store/model';
import { rootReducer } from '../store/reducers';

import { HomeComponent } from './home.component';
import { AppMaterialModule } from '../shared/app-material-module';
import { AppCovalentModuleModule } from '../shared/app-covalent-module.module';
import { RouterTestingModule } from '@angular/router/testing';
import { CommandGatewayModule } from '../shared/command-gateway/command-gateway.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { InventurModule } from '../inventur/inventur.module';
import { NgRedux, NgReduxModule } from '@angular-redux/store';

describe('HomeComponent', () => {
    let component: HomeComponent;
    let fixture: ComponentFixture<HomeComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [HomeComponent],
            imports: [
                AppMaterialModule,
                AppCovalentModuleModule,
                CommandGatewayModule,
                HttpClientTestingModule,
                InventurModule,
                NgReduxModule,
                RouterTestingModule.withRoutes([
                    {path: 'inventur', redirectTo: 'http://www.google.de'},
                ])
            ]
        })
            .compileComponents();
    }));

    beforeEach(inject([NgRedux], (store: NgRedux<AppState>) => {
        store.configureStore(rootReducer, APP_INITIAL_STATE)

        fixture = TestBed.createComponent(HomeComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
