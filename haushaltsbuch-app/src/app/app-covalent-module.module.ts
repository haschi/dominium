import { NgModule } from '@angular/core';
import {
    CovalentCommonModule,
    CovalentLayoutModule,
    CovalentMediaModule,
    CovalentMenuModule,
    CovalentNotificationsModule
} from '@covalent/core';

@NgModule({
    exports: [
        CovalentCommonModule,
        CovalentLayoutModule,
        CovalentNotificationsModule,
        CovalentMenuModule,
        CovalentMediaModule
    ],
    declarations: []
})
export class AppCovalentModuleModule {
}
