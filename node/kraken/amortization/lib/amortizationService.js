//'use strict';

var Loan = require('../models/loan');
var AmortizationFormula = require('./amortizationFormula');
var Amortization = require('../models/amortization');

function AmortizationService(loan){
	this.loan = loan;
	this.amortizationFormula = AmortizationFormula(loan);
}

AmortizationService.prototype.generateAmortizationSchedule = function(loan){
	amortizationFormula = new AmortizationFormula(loan);
	var paymentList = [];
	paymentList = amortizationFormula.generatePayments(loan);

	var amortization = new Amortization();
	amortization.payments = paymentList;

	return amortization;
}

module.exports = AmortizationService;
