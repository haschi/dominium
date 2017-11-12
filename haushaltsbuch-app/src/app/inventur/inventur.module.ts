import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventurComponent } from './inventur.component';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppMaterialModule } from '../shared/app-material-module';
import { AppCovalentModuleModule } from '../shared/app-covalent-module.module';
import { ReactiveFormsModule } from '@angular/forms';
import { PositionComponent } from './position/position.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { HttpClientModule } from '@angular/common/http';
import { LoggerService } from '../shared/logger.service';

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        AppMaterialModule,
        AppCovalentModuleModule,
        ReactiveFormsModule,
        CurrencyMaskModule
    ],
    declarations: [
        InventurComponent,
        PositionComponent
    ],
    exports: [
        InventurComponent
    ],
    providers: [
        LoggerService,
    ]
})
export class InventurModule {
}
