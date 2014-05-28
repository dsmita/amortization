module.exports = {
	isValidLoanAmount : function(loanAmount) {
		return loanAmount >= 0.01 && loanAmount <= 1000000000000;
	},
	isValidLoanTerm : function(term) {
		return term >= 1 && term <= 1000000;
	},
	isValidInterestRate : function(interestRate) {
		return interestRate >= 0.000001 && interestRate <= 100;
	}
};