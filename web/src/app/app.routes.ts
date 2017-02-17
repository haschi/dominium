import { Routes } from '@angular/router';
import { HomeComponent } from './home';
import { AboutComponent } from './about';
import { NoContentComponent } from './no-content';
import { LeererInhaltComponent } from './leerer-inhalt/leerer-inhalt.component';

export const ROUTES: Routes = [
    {path: '', component: HomeComponent},
    {path: 'home', component: HomeComponent},
    {path: 'about', component: AboutComponent},
    {path: 'leer', component: LeererInhaltComponent},
    {path: 'dashboard/:id', component: LeererInhaltComponent},
    {
        path: 'detail', loadChildren: () => System.import('./+detail')
        .then((comp: any) => comp.default),
    },
    {path: '**', component: NoContentComponent},
];
