import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BilanzServiceService } from './bilanz/bilanz-service.service';
import { InventurComponent } from './inventur.component';
import { GruppeComponent } from './gruppe/gruppe.component';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../shared/shared.module';
import { PostenComponent } from './gruppe/posten/posten.component';
import { AppCovalentModuleModule } from '../shared/app-covalent-module.module';
import { InventarComponent } from './inventar/inventar.component';
import { InventurService } from './inventur.service';
import { QueryGatewayModule } from '../shared/query-gateway/query-gateway.module';
import { PositionComponent } from './inventar/position/position.component';
import { ZeileComponent } from './inventar/zeile/zeile.component';
import { BilanzComponent } from './bilanz/bilanz.component';
import { AktivaComponent } from './bilanz/aktiva/aktiva.component';
import { PassivaComponent } from './bilanz/passiva/passiva.component';

@NgModule({
    imports: [
        HttpClientModule,
        AppCovalentModuleModule,
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
        PassivaComponent
    ],
    providers: [InventurService, BilanzServiceService],
    exports: [InventurComponent]
})
export class InventurModule {
}
