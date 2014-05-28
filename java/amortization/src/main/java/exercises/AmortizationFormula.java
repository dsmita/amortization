package exercises;

import java.util.ArrayList;
import java.util.List;

import exercises.model.Loan;
import exercises.model.Payment;

public class AmortizationFormula {

  /**
   * M = P * (J / (1 - (Math.pow(1/(1 + J), N)))); 
   * 
   * Where: 
   * P = Principal 
   * I = Interest 
   * J = Monthly Interest in decimal form: I / (12 * 100) 
   * N = Number of months of loan 
   * M = Monthly Payment Amount
   * 
   */
  public long calculateMonthlyPaymentInCent(Loan loan) {

    long principalInCent = Math.round(loan.getAmount() * 100); // P
    double annualInterestRate = loan.getAnnualInterestRate(); // I
    double monthlyInterest = annualInterestRate / (12d * 100d); // J
    int loanTermInMonths = loan.getYears() * 12; // N

    double tmp = Math.pow(1d + monthlyInterest, -1); // 1 / (1 + J)
    tmp = Math.pow(tmp, loanTermInMonths); // Math.pow(1/(1 + J), N)
    tmp = Math.pow(1d - tmp, -1); // 1 / (1 - (Math.pow(1/(1 + J), N))))

    /** M = P * (J / (1 - (Math.pow(1/(1 + J), N)))); */
    double rc = principalInCent * monthlyInterest * tmp;
    return Math.round(rc);
  }

  /**
   * To create the payment schedule, run the following steps in a loop:
   * 1. Calculate current monthly interest H = P x J
   * 2. Calculate the amount of principal you pay for that month i.e. monthly payment minus your monthly interest C = M - H
   * 3. Calculate new balance of your principal Q = P - C
   * 4. Set P equal to Q and go back to Step 1: You thusly loop around until the value Q (and hence P) goes to zero.
   */
  public List<Payment> generatePayments(Loan loan) {

    long principalInCent = Math.round(loan.getAmount() * 100);
    double annualInterestRate = loan.getAnnualInterestRate();
    double monthlyInterest = annualInterestRate / (12d * 100d);
    int loanTermInMonths = loan.getYears() * 12;

    long monthlyPaymentInCents = calculateMonthlyPaymentInCent(loan);

    long balance = principalInCent;
    int paymentNumber = 0;
    long totalPayments = 0;
    long totalInterestPaid = 0;

    Payment payment =
        new Payment(paymentNumber++, 0d, 0d, ((double) balance) / 100d,
            ((double) totalPayments) / 100d, ((double) totalInterestPaid) / 100d);

    List<Payment> paymentList = new ArrayList<Payment>();
    paymentList.add(payment);

    final int maxNumberOfPayments = loanTermInMonths + 1;

    while ((balance > 0) && (paymentNumber <= maxNumberOfPayments)) {

      // Calculate H = P x J, this is your current monthly interest
      long curMonthlyInterest = Math.round(((double) balance) * monthlyInterest);

      long curPayoffAmount = balance + curMonthlyInterest;

      // the amount to payoff the remaining balance may be less than the calculated
      // monthlyPaymentAmount
      long curMonthlyPaymentAmount = Math.min(monthlyPaymentInCents, curPayoffAmount);

      // it's possible that the calculated monthlyPaymentAmount is 0,
      // or the monthly payment only covers the interest payment - i.e. no principal
      // so the last payment needs to payoff the loan
      if ((paymentNumber == maxNumberOfPayments)
          && ((curMonthlyPaymentAmount == 0) || (curMonthlyPaymentAmount == curMonthlyInterest))) {
        curMonthlyPaymentAmount = curPayoffAmount;
      }

      // principal to be paid that month : C = M - H
      long curMonthlyPrincipalPaid = curMonthlyPaymentAmount - curMonthlyInterest;

      // new balance for the principal : Q = P - C
      long currentBalance = balance - curMonthlyPrincipalPaid;

      totalPayments += curMonthlyPaymentAmount;
      totalInterestPaid += curMonthlyInterest;

      // output is in dollars
      paymentList.add(new Payment(paymentNumber++, ((double) curMonthlyPaymentAmount) / 100d,
          ((double) curMonthlyInterest) / 100d, ((double) currentBalance) / 100d,
          ((double) totalPayments) / 100d, ((double) totalInterestPaid) / 100d));

      balance = currentBalance;
    }
    return paymentList;
  }

  public long getAmountInCents(double amountInDollars) {
    return Math.round(amountInDollars * 100);
  }
}
