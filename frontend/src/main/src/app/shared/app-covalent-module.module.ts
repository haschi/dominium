import { NgModule } from '@angular/core';
import {
    CovalentCommonModule,
    CovalentJsonFormatterModule,
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
        CovalentMediaModule,
        CovalentJsonFormatterModule
    ],
    declarations: []
})
export class AppCovalentModuleModule {
}
