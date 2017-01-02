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

const ExtractTextPlugin = require("extract-text-webpack-plugin");
module.exports = function (options) {
    isProd = options.env === 'production';
    return {
        // ...
        module: {
            rules: [
                // ...
                // Materialize CSS aus dem node_modules Verzeichnis laden
                // mit style-loader und css-loader.
                {
                    test: /\.css$/,
                    include: helpers.root("node_modules"),
                    loader: 'style-loader!css-loader'
                },
                
                // Alle Stylesheets aus dem Verzeichnis src/assets/css 
                // Verzeichnis mit dem ExtractTextPlugin und raw-loader
                // laden.
                {
                    test: helpers.root("src/assets/css"),
                    loader: ExtractTextPlugin.extract({
                        fallbackLoader: "style-loader",
                        loader: "raw-loader"
                    })
                },
                
                // Die Materialize CSS Fonts (Roboto etc) im Verzeichnis
                // node_modules mit dem file-loader laden. Benötigt 
                // roboto-fontface
                {
                    test: /.(png|woff(2)?|eot|ttf|svg)(\?[a-z0-9=\.]+)?$/,
                    include: helpers.root("node_modules"),
                    loader: 'file-loader'
                }
            ]
        },

        plugins: [
            new ExtractTextPlugin({
                filename: "styles.css",
                allChunks: true
            }),

            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: 'jquery',
                'window.$': 'jquery', // damit materialize.js functioniert.
                'window.jQuery': 'jquery',
                // "Hammer": "hammerjs/hammer"
            })
        ]
    }
};
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