import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { BilanzComponent } from './inventur/bilanz/bilanz.component';
import { InventurComponent } from './inventur/inventur.component';
import { InventarComponent } from './inventur/inventar/inventar.component';

export const DEMO_APP_ROUTES: Routes = [
    {path: 'home', component: HomeComponent},
    {path: 'inventur/:id', component: InventurComponent},
    {path: 'inventur/bilanz/:id', component: BilanzComponent},
    {path: 'inventar/:id', component: InventarComponent},
    {path: '**', component: HomeComponent}
];
