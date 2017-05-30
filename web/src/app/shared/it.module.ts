/**
 * Created by matthias on 25.03.17.
 */

import { NgModule } from '@angular/core';
import { HttpTestModule } from './httptest.module';
import { ReduxTestModule } from '../reduxtest.module';
import { AppRouterTestingModule } from '../app-routing.module';

@NgModule({
    imports: [
        ReduxTestModule,
        HttpTestModule,
    ],
    exports: [
        ReduxTestModule,
        HttpTestModule,
    ]
})
export class IntegrationTestingModule {

}
