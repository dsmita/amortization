package exercises;

public class LoanValidator {

  static final double[] borrowAmountRange = new double[] {0.01d, 1000000000000d};
  static final double[] annualInterestRange = new double[] {0.000001d, 100d};
  static final int[] termRange = new int[] {1, 1000000};

  public boolean isValidBorrowAmount(double amount) {
    double range[] = borrowAmountRange;
    return ((range[0] <= amount) && (amount <= range[1]));
  }

  public boolean isValidAnnualInterest(double interestRate) {
    return ((annualInterestRange[0] <= interestRate) && (interestRate <= annualInterestRange[1]));
  }

  public boolean isValidTerm(int years) {
    return ((termRange[0] <= years) && (years <= termRange[1]));
  }

}