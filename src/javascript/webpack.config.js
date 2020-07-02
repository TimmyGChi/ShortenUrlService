const webpack = require('webpack');
const path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const DefinePlugin       = require('webpack/lib/DefinePlugin');

const ENV  = process.env.NODE_ENV = 'development';
const HOST = process.env.HOST || 'localhost';
const PORT = process.env.PORT || 8180;

const metadata = {
    env    : ENV,
    host   : HOST,
    port   : PORT
};

var extractTextPlugin = new ExtractTextPlugin({filename: './../../stylesheets/myapp.css'});
var combineAllStylesIntoOneFile = new ExtractTextPlugin({filename: './../../stylesheets/myapp.css', allChunks:true}); //, {allChunks:true}


module.exports = {
    entry: {
        'myapp' : './myapp/main.ts',
        'vendor': './myapp/vendor.ts'
    },
    mode: 'development',
    resolve: {
        extensions: ['.js', '.ts', '.json']
    },

    module: {
        rules: [
            {
                test: /\.less$/,
                loader: 'raw-loader!less-loader',
                exclude: [/node_modules/]
            },
            {
                test: /\.css$/,
                loader: 'raw-loader', exclude: /node_modules/
            },
            {
                test: /\.(png|woff|woff2|eot|ttf|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: 'shortenUrl-loader'
            },
            {
                test: /\.html$/,
                loader: 'raw-loader'
            },
            {
                test: /\.ts$/,
                use: [
                    { loader: 'cache-loader' },
                    {
                        loader: 'thread-loader',
                        options: {
                            workers: require('os').cpus().length - 1,
                            poolTimeout: 30000
                        },
                    },
                    {
                        loader: 'ts-loader',
                        options: {
                            happyPackMode: true,
                            compilerOptions: {noEmit: false}
                        }
                    }
                ]
            },

            {
                test: require.resolve('jquery'),
                use: [{
                    loader: 'expose-loader',
                    options: 'jQuery'
                },{
                    loader: 'expose-loader',
                    options: '$'
                }]
            }

        ]
    },

    output: {
        path       : __dirname + '/../../grails-app/assets/javascripts/dist/',
        publicPath : './assets/',
        filename   : '[name].bundle.js'
    },

    plugins: [
        // Resolves Critical Dependency warning.
        // https://github.com/angular/angular/issues/11580
        new webpack.ContextReplacementPlugin(
            // if you have anymore problems tweet me at @gdi2290
            // The (\\|\/) piece accounts for path separators for Windows and MacOS
            /(.+)?angular(\\|\/)core(.+)?/,
            path.join(__dirname, 'myapp'), // location of your src
            {} // a map of your routes
        ),
        new DefinePlugin({'webpack': {'ENV': JSON.stringify(metadata.env)}}),
        function () {
            this.plugin('watch-run', function (watching, callback) {
                console.log(' ');
                console.log('Begin compile at ' + new Date());
                callback();
            })
        },
        combineAllStylesIntoOneFile
    ]
};
