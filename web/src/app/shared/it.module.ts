/**
 * Created by matthias on 25.03.17.
 */

import { NgModule } from '@angular/core';
import { HttpTestModule } from './httptest.module';
import { ReduxTestModule } from '../reduxtest.module';
import { AppRouterTestingModule } from '../app-routing.module';
import { CovalentCoreModule, CovalentLayoutModule } from '@covalent/core';

@NgModule({
    imports: [
        ReduxTestModule,
        HttpTestModule,
        CovalentCoreModule.forRoot(),
        CovalentLayoutModule.forRoot()
    ],
    exports: [
        ReduxTestModule,
        HttpTestModule,
        CovalentCoreModule,
        CovalentLayoutModule
    ]
})
export class IntegrationTestingModule {

}
