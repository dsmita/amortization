var Loan = require('../model/loan');
var AmortizationFormula = require('./amortizationFormula');

function AmortizationService(loan){
	this.loan = loan;
	this.amortizationFormula = AmortizationFormula(loan);
}

AmortizationService.prototype.generateAmortizationSchedule = function(loan){
	amortizationFormula = new AmortizationFormula(loan);
	var paymentList = [];
	paymentList = amortizationFormula.generatePayments(loan);
	return paymentList;
}

module.exports = AmortizationService;