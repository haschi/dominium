import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QueryProgressComponent } from './query-progress.component';
import { AppMaterialModule } from '../../app-material-module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { StoreModule } from '../../../store/store.module';
import { CommandGatewayModule } from '../../command-gateway/command-gateway.module';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LoggerService } from '../../logger.service';
import { QueryGatewayModule } from '../query-gateway.module';

describe('QueryProgressComponent', () => {
    let component: QueryProgressComponent;
    let fixture: ComponentFixture<QueryProgressComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            providers: [LoggerService],
            imports: [BrowserModule,
                BrowserAnimationsModule,
                AppMaterialModule,
                StoreModule,
                CommandGatewayModule,
                QueryGatewayModule,
                HttpClientTestingModule
            ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(QueryProgressComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
