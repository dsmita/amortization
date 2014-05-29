'use strict';

module.exports = {
	isValidLoanAmount : function(loanAmount) {
		if (loanAmount < 0.01 || loanAmount > 1000000000000) {
			return false;
		} else {
			return true;
		}
	},
	isValidLoanTerm : function(term) {
		if (term < 1 || term > 1000000) {
			return false;
		} else {
			return true;
		}
	},
	isValidInterestRate : function(interestRate) {
		return interestRate >= 0.000001 && interestRate <= 100;
	}
};