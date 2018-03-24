import { NgModule } from '@angular/core';
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

@NgModule({
    imports: [
        HttpClientModule,
        AppCovalentModuleModule,
        SharedModule,
        QueryGatewayModule
    ],
    declarations: [
        InventurComponent,
        GruppeComponent,
        PostenComponent,
        InventarComponent,
        PositionComponent,
        ZeileComponent
    ],
    providers: [InventurService],
    exports: [InventurComponent]
})
export class InventurModule {
}
