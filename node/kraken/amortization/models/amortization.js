'use strict';
var Payment = require('./payment');

function Amortization(){
    this.payments = [];
}

Payment.prototype.setPayments = function(payments){
	this.payments = payments;
}

module.exports = Amortization;