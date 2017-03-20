import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NoContentComponent } from './no-content/no-content.component';
import { LeererInhaltComponent } from './leerer-inhalt/leerer-inhalt.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { RouterTestingModule } from '@angular/router/testing';

const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'home', component: HomeComponent},
    {path: 'about', component: AboutComponent},
    {path: 'leer', component: LeererInhaltComponent},
    {path: 'dashboard/:id', component: LeererInhaltComponent},
    // {
    //     path: 'detail', loadChildren: () => System.import('./+detail')
    //     .then((comp: any) => comp.default),
    // },
    {path: '**', component: NoContentComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
    providers: []
})
export class AppRoutingModule { }

@NgModule({
    imports: [RouterTestingModule.withRoutes(routes)],
    exports: [RouterTestingModule],
    providers: []
})
export class AppRouterTestingModule { }
