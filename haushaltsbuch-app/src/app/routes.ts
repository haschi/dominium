import {Routes} from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AppComponent } from './app.component';

export const DEMO_APP_ROUTES: Routes = [
  {path: '', component: AppComponent, children: [
    {path: '', component: HomeComponent},
  ]}
];

export const ALL_ROUTES: Routes = [
  {path: '',  component: AppComponent, children: DEMO_APP_ROUTES}
];
