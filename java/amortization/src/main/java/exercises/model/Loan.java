package exercises.model;

public class Loan {

  private double amount;
  private double annualInterestRate;
  private int years;

  public Loan(double amount, double annualInterestRate, int years) {
    this.amount = amount;
    this.annualInterestRate = annualInterestRate;
    this.years = years;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getAnnualInterestRate() {
    return annualInterestRate;
  }

  public void setAnnualInterestRate(double annualInterestRate) {
    this.annualInterestRate = annualInterestRate;
  }

  public int getYears() {
    return years;
  }

  public void setYears(int years) {
    this.years = years;
  }

}
