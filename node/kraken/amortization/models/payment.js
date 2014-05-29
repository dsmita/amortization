'use strict';

function Payment(){
	this.number = 0;
    this.amount = 0;
    this.interest = 0;
    this.currentBalance = 0;
    this.totalPayments = 0;
    this.totalInterestPaid = 0;
}

Payment.prototype.setNumber = function(number){
	this.number = number;
}

Payment.prototype.setAmount = function(amount){
	this.amount = amount;
}

Payment.prototype.setInterest = function(interest){
	this.interest = interest;
}

Payment.prototype.setCurrentBalance = function(currentBalance){
	this.currentBalance = currentBalance;
}

Payment.prototype.setTotalPayments = function(totalPayments){
	this.totalPayments = totalPayments;
}

Payment.prototype.setTotalInterestPaid = function(totalInterestPaid){
	this.totalInterestPaid = totalInterestPaid;
}


module.exports = Payment;