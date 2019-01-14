var sys = require('sys');
var exec = require('child_process').exec;
function puts(error, stdout, stderr) { sys.puts(stdout) }

var os = require('os');

if (process.argv.length === 0) {
    throw new Error("Environment not defined");
}

if (os.type() === 'Linux' ||
    os.type() === 'Darwin')

    if (process.argv[0] === 'development') {
        exec("sh -ac '. .env.development; react-scripts build'", puts);
    }

    if (process.argv[0] === 'local') {
        exec("sh -ac '. .env.local; react-scripts build'", puts);
    }

    if (process.argv[0] === 'staging') {
        exec("sh -ac '. .env.staging; react-scripts build'", puts);
    }

    if (process.argv[0] === 'production') {
        exec("sh -ac '. .env.production; react-scripts build'", puts);
    }

else if (os.type() === 'Windows_NT')

    if (process.argv[0] === 'development') {
        exec(".env.development && react-scripts build", puts);
    }

    if (process.argv[0] === 'local') {
        exec(".env.local && react-scripts build", puts);
    }

    if (process.argv[0] === 'staging') {
        exec(".env.staging && react-scripts build", puts);
    }

    if (process.argv[0] === 'production') {
        exec(".env.production && react-scripts build", puts);
    }

else
    throw new Error("Unsupported OS found: " + process.env.OS.type());