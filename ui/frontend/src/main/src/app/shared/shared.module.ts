import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppCovalentModuleModule } from './app-covalent-module.module';
import { AppMaterialModule } from './app-material-module';

import { LoggerService } from './logger.service';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material';
import { CovalentJsonFormatterModule } from '@covalent/core';
import { AutofocusDirective } from './autofocus.directive';

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        AppCovalentModuleModule,
        AppMaterialModule
    ],
    exports: [
        CommonModule,
        AppCovalentModuleModule,
        AppMaterialModule,
        ReactiveFormsModule,
        MatInputModule,
        CovalentJsonFormatterModule,
        AutofocusDirective
    ],
    providers: [
        LoggerService,
    ],
    declarations: [AutofocusDirective]
})
export class SharedModule {
}
