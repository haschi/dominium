# Webpack 2 und Materialize CSS

## Materialize CSS
Um [Materialize CSS](http://materializecss.com/) zusammen mit 
[Webpack 2](https://webpack.js.org/) verwenden zu können, wenn mit 
dem [Angular Starter Kit](https://github.com/AngularClass/angular2-webpack-starter) 
begonnen wird, gibt es eine Lösung, die 
[hier](https://github.com/InfomediaLtd/angular2-materialize/issues/2)
beschrieben ist:

Folgende npm Pakete werden benötigt:
```bash
npm install imports-loader --save-save
npm install url-loader --save-dev
```

In webpack.common.js:
```javascript
    { test: /materialize\.css$/,   loader: 'style-loader!css-loader' },
    // Support for CSS as raw text (do not match 'materialize')
    { test: /^((?!materialize).)*\.css$/,   loader: 'raw-loader' },
    { test: /.(png|woff(2)?|eot|ttf|svg)(\?[a-z0-9=\.]+)?$/, loader: 'url-loader?limit=100000' },
```

## Materialize JS

Die zweite Hürde besteht darin, materialize.js funktionstüchtig zu bekommen.
Siehe [Shimming](https://webpack.js.org/guides/shimming/) in 
[Webpack Guides](https://webpack.js.org/guides/)
in webpack.commin.js:
```javascript
       plugins: [
            new webpack.ProvidePlugin({
                $: "jquery",
                $: 'jquery',
                jQuery: 'jquery',
                'window.$': 'jquery', // damit materialize.js functioniert.
                'window.jQuery': 'jquery',
                // "Hammer": "hammerjs/hammer"
            })
        ]
```