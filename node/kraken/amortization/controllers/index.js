//'use strict';


var IndexModel = require('../models/index');
var loanValidator = require('../lib/loanValidator');
var Loan = require('../models/loan');
var AmortizationService = require('../lib/amortizationService');


module.exports = function (router) {

    var model = new IndexModel();


    router.get('/', function (req, res) {
        res.render('index', model);
    });

    router.post('/loan', function(req, res)
    {

        var loan = new Loan();
        loan.amount = req.body.amount;
        loan.interestRate = req.body.interestRate;
        loan.term = req.body.term;

        console.log("amount: ", loan.amount);
        console.log("interestRate: ", loan.interestRate);
        console.log("term: ", loan.term);

        newLoanApplication(loan, function(loan, error) {

            if(error){
                console.log("invalid");
                res.render("errors/badinput", error);
            }
            else{

                console.log("valid");
                var amortizationService = new AmortizationService(loan);

                var amortization = amortizationService.generateAmortizationSchedule(loan);

                //console.log(amortization);

                res.render('amortization', amortization);
            }

        });


    });

};

function newLoanApplication(loan, callback) {
    readAndValidateLoanAmount(loan, function(loan, error) {
        if(error){
            callback(loan, error);
        }
        else{
            readAndValidateAnnualInterestRate(loan, function(loan, error) {
                if(error){
                    callback(loan, error);
                }
                else{
                    readAndValidateLoanTerm(loan, function(loan, error) {
                        callback(loan, error);
                    });   
                }

            });
        }

    });
}

function readAndValidateLoanAmount(loan, callback) {

    if (loanValidator.isValidLoanAmount(loan.amount) == true) {
            callback(loan);
        } else {
            var errorMessage = "Please enter a positive value of amount between " + 0.01
                    + " and " + 1000000000000 + ". ";
            console.log(errorMessage);
            callback(loan, {field: "Loan amount", message: errorMessage});
        }
}

function readAndValidateAnnualInterestRate(loan, callback) {
        if (loanValidator.isValidInterestRate(loan.interestRate) == true) {
            callback(loan);
        } else {
            var errorMessage = "Please enter a positive value between " + 0.000001
                    + " and " + 100 + ". ";
            console.log(errorMessage);
            callback(loan, {field: "Interest Rate", message: errorMessage});
        }
}

function readAndValidateLoanTerm(loan, callback) {
        if (loanValidator.isValidLoanTerm(loan.term)==true) {
            callback(loan);
        } else {
            var errorMessage = "Please enter a positive integer value of term between " + 1
                    + " and " + 1000000 + ". ";
            console.log(errorMessage);
            callback(loan, {field: "Loan Term", message: errorMessage});
        }
}
