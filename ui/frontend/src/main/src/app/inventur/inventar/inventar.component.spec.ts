import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventarComponent } from './inventar.component';
import { AppMaterialModule } from '../../shared/app-material-module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppCovalentModuleModule } from '../../shared/app-covalent-module.module';
import { QueryGatewayModule } from '../../shared/query-gateway/query-gateway.module';

xdescribe('InventarComponent', () => {
    let component: InventarComponent;
    let fixture: ComponentFixture<InventarComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                BrowserModule,
                BrowserAnimationsModule,
                AppMaterialModule,
                AppCovalentModuleModule,
                QueryGatewayModule,
            ],
            declarations: [InventarComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(InventarComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
