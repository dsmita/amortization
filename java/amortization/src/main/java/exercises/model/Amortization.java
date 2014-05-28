package exercises.model;

import java.util.List;

public class Amortization {

  private Loan loan;
  private List<Payment> payments;

  public Amortization(Loan loan, List<Payment> payments) {
    this.loan = loan;
    this.payments = payments;
  }

  public Loan getLoan() {
    return loan;
  }

  public void setLoan(Loan loan) {
    this.loan = loan;
  }

  public List<Payment> getPayments() {
    return payments;
  }

  public void setPayments(List<Payment> payments) {
    this.payments = payments;
  }

}
