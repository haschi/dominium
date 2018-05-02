import { HttpClient } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EpicProvider } from '../redux-utils/provider';
import { CommandGatewayService } from './command-gateway.service';
import { fallsCommandAngefordert } from './command.redux';
import { IdGeneratorService } from './id-generator.service';
import { CommandProgressComponent } from './command-progress/command-progress.component';
import { AppMaterialModule } from '../app-material-module';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CovalentDialogsModule, TdDialogService } from '@covalent/core';

@NgModule({
    providers: [
        CommandGatewayService,
        IdGeneratorService,
        EpicProvider(fallsCommandAngefordert, [HttpClient, TdDialogService])
    ],
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        AppMaterialModule,
        CovalentDialogsModule
    ],
    declarations: [CommandProgressComponent],
    exports: [CommandProgressComponent]
})
export class CommandGatewayModule {
}
