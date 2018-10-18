var webpack = require('webpack');

module.exports = {

    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: ['babel-loader'],
            }
        ]
    },

    entry: [
        './src/index.jsx'
    ],

    output: {
        filename: './dist/app.js',
    }
};