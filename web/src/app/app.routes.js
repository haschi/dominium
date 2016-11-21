"use strict";
var home_1 = require('./home');
var about_1 = require('./about');
var no_content_1 = require('./no-content');
exports.ROUTES = [
    { path: '', component: home_1.HomeComponent },
    { path: 'home', component: home_1.HomeComponent },
    { path: 'about', component: about_1.AboutComponent },
    {
        path: 'detail', loadChildren: function () { return System.import('./+detail').then(function (comp) {
            return comp.default;
        }); }
    },
    { path: '**', component: no_content_1.NoContentComponent },
];
//# sourceMappingURL=app.routes.js.map