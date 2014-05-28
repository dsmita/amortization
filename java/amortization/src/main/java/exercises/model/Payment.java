package exercises.model;

public class Payment {

  private int number;
  private double amount;
  private double interest;
  private double currentBalance;
  private double totalPayments;
  private double totalInterestPaid;

  public Payment(int number, double amount, double interest, double currentBalance,
      double totalPayments, double totalInterestPaid) {
    this.number = number;
    this.amount = amount;
    this.interest = interest;
    this.currentBalance = currentBalance;
    this.totalPayments = totalPayments;
    this.totalInterestPaid = totalInterestPaid;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getInterest() {
    return interest;
  }

  public void setInterest(double interest) {
    this.interest = interest;
  }

  public double getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(double currentBalance) {
    this.currentBalance = currentBalance;
  }

  public double getTotalPayments() {
    return totalPayments;
  }

  public void setTotalPayments(double totalPayments) {
    this.totalPayments = totalPayments;
  }

  public double getTotalInterestPaid() {
    return totalInterestPaid;
  }

  public void setTotalInterestPaid(double totalInterestPaid) {
    this.totalInterestPaid = totalInterestPaid;
  }

}
