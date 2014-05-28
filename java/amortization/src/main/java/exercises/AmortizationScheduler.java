package exercises;

import java.util.List;

import exercises.model.Amortization;
import exercises.model.Loan;
import exercises.model.Payment;

public class AmortizationScheduler {

  private AmortizationFormula formula;
  private Loan loan;

  public AmortizationScheduler(Loan loan) {
    this.loan = loan;
    this.formula = new AmortizationFormula();
    init();
  }

  private void init() {

    long monthlyPaymentInCent = formula.calculateMonthlyPaymentInCent(loan);

    // the following shouldn't happen with the available valid ranges
    // for borrow amount, apr, and term; however, without range validation,
    // monthlyPaymentAmount as calculated by calculateMonthlyPayment()
    // may yield incorrect values with extreme input values
    if (monthlyPaymentInCent > formula.getAmountInCents(loan.getAmount())) {
      throw new IllegalArgumentException();
    }
  }

  public Amortization generatePaymentSchedule() {
    List<Payment> payments = formula.generatePayments(loan);
    return new Amortization(loan, payments);
  }

}
