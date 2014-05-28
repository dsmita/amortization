
const AMOUNT_PROMPT = "Please enter the amount you would like to borrow: ";
const RATE_PROMPT = "Please enter the annual percentage rate used to repay the loan: ";
const TERM_PROMPT = "Please enter the term, in years, over which the loan is repaid: ";

var readLine = require('readline');
var loanValidator = require('./lib/loanValidator');
var Loan = require('./model/loan');
var AmortizationService = require('./lib/amortizationService');

var rl = readLine.createInterface({
	input : process.stdin,
	output : process.stdout
});

function newLoanApplication(loan, callback) {
	readAndValidateLoanAmount(loan, function() {
		readAndValidateAnnualInterestRate(loan, function() {
			readAndValidateLoanTerm(loan, function() {
				callback(loan);
			});
		});
	});
}

function readAndValidateLoanAmount(loan, callback) {
	rl.question(AMOUNT_PROMPT, function(loanAmount) {
		if (loanValidator.isValidLoanAmount(loanAmount) == true) {
			loan.setAmount(loanAmount);
			callback(loan);
		} else {
			console.log("Please enter a positive value between " + 0.01
					+ " and " + 1000000000000 + ". ");
			readAndValidateLoanAmount(loan, callback);
		}
	});
}

function readAndValidateAnnualInterestRate(loan, callback) {
	rl.question(RATE_PROMPT, function(interestRate) {
		if (loanValidator.isValidInterestRate(interestRate) == true) {
			loan.setInterestRate(interestRate);
			callback(loan);
		} else {
			console.log("Please enter a positive value between " + 0.000001
					+ " and " + 100 + ". ");
			readAndValidateAnnualInterestRate(loan, callback);
		}
	});
}

function readAndValidateLoanTerm(loan, callback) {
	rl.question(TERM_PROMPT, function(term) {
		if (loanValidator.isValidLoanTerm(term) == true) {
			loan.setTerm(term);
			callback(loan);
		} else {
			console.log("Please enter a positive integer value between " + 1
					+ " and " + 1000000 + ". ");
			readAndValidateLoanTerm(loan, callback);
		}
	});
}

var loan = new Loan();
newLoanApplication(loan, function() {
	var amortizationService = new AmortizationService(loan);
	var paymentList = [];
	paymentList = amortizationService.generateAmortizationSchedule(loan);
	console.log("PaymentNumber", "PaymentAmount", "PaymentInterest",
          "CurrentBalance", "TotalPayments", "TotalInterestPaid")
	for (var i = 0; i < paymentList.length; i++) {
		var payment = paymentList[i];

    	console.log(payment.number + ", "+payment.amount +", " +payment.interest +", "+payment.currentBalance +", "+payment.totalPayments
    		+", "+payment.totalInterestPaid );
	}
	rl.close();
});
