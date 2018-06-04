import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppMaterialModule } from '../shared/app-material-module';
import { EpicProvider } from '../shared/redux-utils/provider';
import { BilanzService } from './bilanz/bilanz.service';
import { EingabeDialog } from './eingabe-dialog.component';
import { InventurComponent } from './inventur.component';
import { GruppeComponent } from './gruppe/gruppe.component';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../shared/shared.module';
import { PostenComponent } from './gruppe/posten/posten.component';
import { AppCovalentModuleModule } from '../shared/app-covalent-module.module';
import { InventarComponent } from './inventar/inventar.component';
import { fallsQueryInventurGruppenGelesenGelungen } from './shared/gruppen.redux';
import { InventarEingabeService } from './shared/inventar-eingabe.service';
import {
    fallsCommandInventurBegonnenGelungen, fallsInventarErfasstGelungen,
    fallsInventarLesenFehlgeschlagen,
    fallsQueryEroeffnungsbilanzGelesenGelungen,
    fallsQueryInventarGelesenGelungen
} from './shared/inventur.redux';
import { InventurService } from './inventur.service';
import { QueryGatewayModule } from '../shared/query-gateway/query-gateway.module';
import { PositionComponent } from './inventar/position/position.component';
import { ZeileComponent } from './inventar/zeile/zeile.component';
import { BilanzComponent } from './bilanz/bilanz.component';
import { AktivaComponent } from './bilanz/aktiva/aktiva.component';
import { PassivaComponent } from './bilanz/passiva/passiva.component';
import { NavigatorComponent } from './navigator/navigator.component';

@NgModule({
    imports: [
        HttpClientModule,
        AppCovalentModuleModule,
        AppMaterialModule,
        SharedModule,
        QueryGatewayModule,
        RouterModule
    ],
    declarations: [
        InventurComponent,
        GruppeComponent,
        PostenComponent,
        InventarComponent,
        PositionComponent,
        ZeileComponent,
        BilanzComponent,
        AktivaComponent,
        PassivaComponent,
        NavigatorComponent,
        EingabeDialog
    ],
    providers: [
        InventurService,
        BilanzService,
        InventarEingabeService,
        EpicProvider(fallsCommandInventurBegonnenGelungen),
        EpicProvider(fallsQueryInventarGelesenGelungen),
        EpicProvider(fallsQueryEroeffnungsbilanzGelesenGelungen),
        EpicProvider(fallsInventarLesenFehlgeschlagen),
        EpicProvider(fallsQueryInventurGruppenGelesenGelungen),
        EpicProvider(fallsInventarErfasstGelungen)
    ],
    exports: [InventurComponent],
    entryComponents: [EingabeDialog]
})
export class InventurModule {
}
