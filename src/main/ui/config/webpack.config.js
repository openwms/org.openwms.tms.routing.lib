var webpack = require('webpack');

module.exports = {

    module: {
        rules: [
            {
                test: /\.jsx?$/,
                use: [
                    {
                        loader: 'babel-loader'
                    }
                ]
            }
        ]
    },

    entry: [
        './src/index.jsx'
    ],

    output: {
        filename: 'app.js',
    }
};