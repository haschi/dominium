import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { InventurComponent } from './inventur/inventur.component';
import { InventarComponent } from './inventur/inventar/inventar.component';

export const DEMO_APP_ROUTES: Routes = [
    {path: 'home', component: HomeComponent},
    {path: 'inventur', component: InventurComponent},
    {path: 'inventur/:id', component: InventarComponent},
    {path: '**', component: HomeComponent}
];
