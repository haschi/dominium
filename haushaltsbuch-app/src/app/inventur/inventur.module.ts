import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventurComponent } from './inventur.component';
import { ErfassungComponent } from './erfassung/erfassung.component';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpModule } from '@angular/http';
import { AppMaterialModule } from '../shared/app-material-module';
import { AppCovalentModuleModule } from '../shared/app-covalent-module.module';

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        HttpModule,
        AppMaterialModule,
        AppCovalentModuleModule,
    ],
    declarations: [
        InventurComponent,
        ErfassungComponent
    ],
    exports: [
        InventurComponent
    ],
    entryComponents: [
        ErfassungComponent
    ]
})
export class InventurModule {
}
