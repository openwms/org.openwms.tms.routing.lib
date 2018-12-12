After the project has been ejected follow these steps

- Copy the adjusted webpack.config.js to the root folder (.)

- Adjust the npm build steps as follows
    "start": "sh -ac '. .env; node scripts/start.js'",
    "build": "sh -ac '. .env.${REACT_APP_ENV}; node scripts/build.js'",
    "build:dev": "sh -ac '. .env; node scripts/build.js'",
    "build:local": "sh -ac '. .env.local; node scripts/build.js'",
    "build:portal": "sh -ac '. .env.portal; webpack -p --progress'",
    "build:it": "sh -ac '. .env.it; webpack -p --progress'",
    "build:staging": "sh -ac '. .env.staging; webpack -p --progress'",
    "build:production": "sh -ac '. .env.production; webpack -p --progress'",
    "test": "node scripts/test.js --env=jsdom"

 - Install webpack-cli in order to build production bundles
 npm install -D webpack-cli
 
 