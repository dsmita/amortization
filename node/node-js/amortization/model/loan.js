//var amount;
//var interest;
//var term;

function Loan() {
	this.amount = 0;
	this.interestRate = 0;
	this.term = 0;
}

Loan.prototype.setAmount = function (amount) {
	this.amount = amount;
}

Loan.prototype.setInterestRate = function (interestRate) {
	this.interestRate = interestRate;
}

Loan.prototype.setTerm = function (term) {
	this.term = term;
}

module.exports = Loan;