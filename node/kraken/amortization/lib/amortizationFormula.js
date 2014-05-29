//'use strict';

var Loan = require('../models/loan');
var Payment = require('../models/payment');

 function AmortizationFormula(loan){
  this.loan = loan;
  this.amountBorrowedInCents = (loan.amount)*100;
  this.annualInterestRate = loan.interestRate;
  this.initialTermMonths = (loan.term)*12;
  this.monthlyInterestDivisor = 12*100;
  this.monthlyInterest = 0;
  this.monthlyPaymentAmount = 0;

  this.calculateMonthlyPayment = function() {
    // calculate J
    monthlyInterest = annualInterestRate / monthlyInterestDivisor;

    // this is 1 / (1 + J)
    tmp = Math.pow(1 + monthlyInterest, -1);

    // this is Math.pow(1/(1 + J), N)
    tmp = Math.pow(tmp, initialTermMonths);

    // this is 1 / (1 - (Math.pow(1/(1 + J), N))))
    tmp = Math.pow(1 - tmp, -1);

    // M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
    rc = amountBorrowedInCents * monthlyInterest * tmp;

    this.monthlyPaymentAmount = Math.round(rc);
  };

  this.calculateMonthlyPayment();
}

AmortizationFormula.prototype.getAmountInCents = function(amountInDollars) {
    return amountInDollars * 100;
}



  AmortizationFormula.prototype.generatePayments = function(loan) {

    var balance = amountBorrowedInCents;
    var paymentNumber = 0;
    var totalPayments = 0;
    var totalInterestPaid = 0;

    var maxNumberOfPayments = initialTermMonths + 1;
    var paymentList = [];
    
     while ((balance > 0) && (paymentNumber<= maxNumberOfPayments)) {
      var payment = new Payment();
      // Calculate H = P x J, this is your current monthly interest
      var curMonthlyInterest = Math.round(balance * monthlyInterest);

      // the amount required to payoff the loan
      var curPayoffAmount = balance + curMonthlyInterest;
     
      // the amount to payoff the remaining balance may be less than the calculated
      // monthlyPaymentAmount
      var curMonthlyPaymentAmount = Math.min(monthlyPaymentAmount, curPayoffAmount);

      // it's possible that the calculated monthlyPaymentAmount is 0,
      // or the monthly payment only covers the interest payment - i.e. no principal
      // so the last payment needs to payoff the loan
      if ((paymentNumber == maxNumberOfPayments)
          && ((curMonthlyPaymentAmount == 0) || (curMonthlyPaymentAmount == curMonthlyInterest))) {
        curMonthlyPaymentAmount = curPayoffAmount;
      }

      // Calculate C = M - H, this is your monthly payment minus your monthly interest,
      // so it is the amount of principal you pay for that month
      var curMonthlyPrincipalPaid = curMonthlyPaymentAmount - curMonthlyInterest;

      // Calculate Q = P - C, this is the new balance of your principal of your loan.
      var curBalance = balance - curMonthlyPrincipalPaid;

      totalPayments += curMonthlyPaymentAmount;
      
      totalInterestPaid += curMonthlyInterest;

      paymentNumber++;
      payment.setNumber(paymentNumber);
      payment.setAmount(curMonthlyPaymentAmount/100);
      payment.setInterest(curMonthlyInterest/100);
      payment.setCurrentBalance(curBalance/ 100);
      payment.setTotalPayments(totalPayments/100);
      payment.setTotalInterestPaid(totalInterestPaid/100);

      // output is in dollars
      paymentList.push(payment);

      // Set P equal to Q and go back to Step 1: You thusly loop around until the value Q (and hence
      // P) goes to zero.
      balance = curBalance;
    }
    return paymentList;
  }


module.exports = AmortizationFormula;