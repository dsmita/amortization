package exercises;

import java.io.IOException;

import com.google.common.annotations.VisibleForTesting;

import exercises.io.IOChannel;
import exercises.model.Loan;

public class LoanConsole {

  private static final String AMOUNT_PROMPT = "Please enter the amount you would like to borrow: ";
  private static final String RATE_PROMPT =
      "Please enter the annual percentage rate used to repay the loan: ";
  private static final String TERM_PROMPT =
      "Please enter the term, in years, over which the loan is repaid: ";

  private IOChannel channel;

  private LoanValidator loanValidator = new LoanValidator();

  public Loan newLoanApplication() throws IOException {

    double amount = readAndValidateAmount();
    double annualInterestRate = readAndValidateAnnualInterestRate();
    int years = readAndValidateTerm();

    return new Loan(amount, annualInterestRate, years);
  }

  private int readAndValidateTerm() throws IOException {

    String termAsString = channel.read(TERM_PROMPT);
    try {
      int years = Integer.parseInt(termAsString);
      if (loanValidator.isValidTerm(years))
        return years;
      else {
        int[] range = LoanValidator.termRange;
        channel.write("Please enter a positive integer value between " + range[0] + " and "
            + range[1] + ". ");
      }
    } catch (NumberFormatException exception) {
      channel.write("An invalid value was entered.\n");
    }
    return readAndValidateTerm(false);
  }


  // this is a pattern to break infinite loop during testing
  @VisibleForTesting
  int readAndValidateTerm(boolean calledFromTest) throws IOException {
    return readAndValidateTerm();
  }

  private double readAndValidateAnnualInterestRate() throws IOException {

    String interestRateAsString = channel.read(RATE_PROMPT);
    try {
      double interestRate = Double.parseDouble(interestRateAsString);
      if (loanValidator.isValidAnnualInterest(interestRate))
        return interestRate;
      else {
        double[] range = LoanValidator.annualInterestRange;
        channel.write("Please enter a positive value between " + range[0] + " and " + range[1]
            + ". ");
      }
    } catch (NumberFormatException exception) {
      channel.write("An invalid value was entered.\n");
    }
    return readAndValidateAnnualInterestRate(false);
  }

  // this is a pattern to break infinite loop during testing
  @VisibleForTesting
  double readAndValidateAnnualInterestRate(boolean calledFromTest) throws IOException {
    return readAndValidateAnnualInterestRate();
  }

  private double readAndValidateAmount() throws IOException {

    String amountAsString = channel.read(AMOUNT_PROMPT);
    try {
      double amount = Double.parseDouble(amountAsString);
      if (loanValidator.isValidBorrowAmount(amount))
        return amount;
      else {
        double range[] = LoanValidator.borrowAmountRange;
        channel.write("Please enter a positive value between " + range[0] + " and " + range[1]
            + ". ");
      }
    } catch (NumberFormatException exception) {
      channel.write("An invalid value was entered.\n");
    }
    return readAndValidateAmount(false);
  }

  // this is a pattern to break infinite loop during testing
  @VisibleForTesting
  double readAndValidateAmount(boolean calledFromTest) throws IOException {
    return readAndValidateAmount();
  }

  public void setChannel(IOChannel channel) {
    this.channel = channel;
  }

  @VisibleForTesting
  void setLoanValidator(LoanValidator loanValidator) {
    this.loanValidator = loanValidator;
  }

}
