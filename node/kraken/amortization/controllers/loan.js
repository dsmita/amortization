'use strict';


var Loan = require('../models/loan');


module.exports = function (router) {

    router.post('/loan', function(req, res)
    {
    	var amount = req.body.amount;
    	console.log(amount);
    });

};
