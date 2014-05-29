'use strict';


var kraken = require('kraken-js'),
	paramLogger = require('./lib/paramLogger'),
    app = require('express')(),
    options = require('./lib/spec')(app),
    port = process.env.PORT || 8000;


app.use(kraken(options));

app.requestStart = function requestStart(server) {
	console.log(1111111);
	server.use(paramLogger());
};

app.requestBeforeRoute = function requestBeforeRoute(server) {
	console.log(222222);
    server.use(millionsServed());    
};


app.listen(port, function (err) {
    console.log('[%s] Listening on http://localhost:%d', app.settings.env, port);
});