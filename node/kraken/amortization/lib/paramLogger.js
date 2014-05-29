'use strict';

module.exports = function () {
    return function (req, res, next) {

    	console.log("********");
        //console.log(req.body);
        next();
    };
};