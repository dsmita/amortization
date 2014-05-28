package exercises;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import exercises.io.IOChannel;
import exercises.model.Loan;

public class LoanConsoleTest {
  
  private static final String AMOUNT_PROMPT = "Please enter the amount you would like to borrow: ";
  private static final String RATE_PROMPT =
      "Please enter the annual percentage rate used to repay the loan: ";
  private static final String TERM_PROMPT =
      "Please enter the term, in years, over which the loan is repaid: ";

  @Mock
  private IOChannel channel;

  @Spy
  private LoanConsole loanConsole;

  @Spy
  private LoanValidator validator;

  @BeforeMethod
  public void beforeMethod() throws IOException {

    loanConsole = new LoanConsole();
    validator = new LoanValidator();

    MockitoAnnotations.initMocks(this);

    loanConsole.setChannel(channel);
    loanConsole.setLoanValidator(validator);


    when(channel.read(AMOUNT_PROMPT)).thenReturn("10000");
    when(channel.read(RATE_PROMPT)).thenReturn("5");
    when(channel.read(TERM_PROMPT)).thenReturn("3");

  }

  @Test
  public void readsAndValidatesBorrowAmount() throws IOException {

    Loan loan = loanConsole.newLoanApplication();

    verify(channel).read(AMOUNT_PROMPT);
    verify(validator).isValidBorrowAmount(10000.0);

    assertNotNull(loan);
    assertEquals(loan.getAmount(), 10000.0);
  }

  @Test
  public void readsAndValidatesAnnualInterestRate() throws IOException {

    Loan loan = loanConsole.newLoanApplication();

    verify(channel).read(RATE_PROMPT);
    verify(validator).isValidAnnualInterest(5);

    assertNotNull(loan);
    assertEquals(loan.getAnnualInterestRate(), 5.0);
  }

  @Test
  public void readsAndValidatesLoanTerm() throws IOException {

    Loan loan = loanConsole.newLoanApplication();

    verify(channel).read(TERM_PROMPT);
    verify(validator).isValidTerm(3);

    assertNotNull(loan);
    assertEquals(loan.getYears(), 3);
  }

  @Test
  public void repromptsWithErrormessageForInvalidBorrowAmount() throws IOException {

    when(channel.read(AMOUNT_PROMPT)).thenReturn("afd");
    doReturn(10000.0).when(loanConsole).readAndValidateAmount(anyBoolean());

    loanConsole.newLoanApplication();

    verify(channel).write("An invalid value was entered.\n");
    verify(loanConsole).readAndValidateAmount(false);
  }

  @Test
  public void repromptsWithErrormessageIfBorrowAmountIsLessThanMinRange() throws IOException {

    when(channel.read(AMOUNT_PROMPT)).thenReturn("-1");
    doReturn(10000.0).when(loanConsole).readAndValidateAmount(anyBoolean());

    loanConsole.newLoanApplication();

    verify(channel).write(
        "Please enter a positive value between " + 0.01d + " and " + 1000000000000d + ". ");
    verify(loanConsole).readAndValidateAmount(false);
  }

  @Test
  public void repromptsWithErrormessageIfBorrowAmountIsMoreThanMaxRange() throws IOException {

    when(channel.read(AMOUNT_PROMPT)).thenReturn("1000000000001d");
    doReturn(10000.0).when(loanConsole).readAndValidateAmount(anyBoolean());

    loanConsole.newLoanApplication();

    verify(channel).write(
        "Please enter a positive value between " + 0.01d + " and " + 1000000000000d + ". ");
    verify(loanConsole).readAndValidateAmount(false);
  }

  @Test
  public void repromptsWithErrormessageForInvalidInterestRate() throws IOException {

    when(channel.read(RATE_PROMPT)).thenReturn("junk");
    doReturn(1d).when(loanConsole).readAndValidateAnnualInterestRate(anyBoolean());

    loanConsole.newLoanApplication();

    verify(channel).write("An invalid value was entered.\n");
    verify(loanConsole).readAndValidateAnnualInterestRate(false);
  }

  @Test
  public void repromptsWithErrormessageIfInterestRateIsLessThanMinRange() throws IOException {

    when(channel.read(RATE_PROMPT)).thenReturn("0.0000001d");
    doReturn(1d).when(loanConsole).readAndValidateAnnualInterestRate(anyBoolean());

    loanConsole.newLoanApplication();

    verify(channel).write(
        "Please enter a positive value between " + 0.000001d + " and " + 100d + ". ");
    verify(loanConsole).readAndValidateAnnualInterestRate(false);
  }

  @Test
  public void repromptsWithErrormessageIfInterestRateIsMoreThanMaxRange() throws IOException {

    when(channel.read(RATE_PROMPT)).thenReturn("100.1d");
    doReturn(10d).when(loanConsole).readAndValidateAnnualInterestRate(anyBoolean());

    loanConsole.newLoanApplication();

    verify(channel).write(
        "Please enter a positive value between " + 0.000001d + " and " + 100d + ". ");
    verify(loanConsole).readAndValidateAnnualInterestRate(false);
  }

  @Test
  public void repromptsWithErrormessageForInvalidTerm() throws IOException {

    when(channel.read(TERM_PROMPT)).thenReturn("junk");
    doReturn(2).when(loanConsole).readAndValidateTerm(anyBoolean());

    loanConsole.newLoanApplication();

    verify(channel).write("An invalid value was entered.\n");
    verify(loanConsole).readAndValidateTerm(false);
  }

  @Test
  public void repromptsWithErrormessageIfTermIsLessThanMinRange() throws IOException {

    when(channel.read(TERM_PROMPT)).thenReturn("0");
    doReturn(10).when(loanConsole).readAndValidateTerm(anyBoolean());

    loanConsole.newLoanApplication();

    verify(channel).write(
        "Please enter a positive integer value between " + 1 + " and " + 1000000 + ". ");
    verify(loanConsole).readAndValidateTerm(false);
  }

  @Test
  public void repromptsWithErrormessageIfTermIsMoreThanMaxRange() throws IOException {

    when(channel.read(TERM_PROMPT)).thenReturn("1000001");
    doReturn(102).when(loanConsole).readAndValidateTerm(anyBoolean());

    loanConsole.newLoanApplication();

    verify(channel).write(
        "Please enter a positive integer value between " + 1 + " and " + 1000000 + ". ");
    verify(loanConsole).readAndValidateTerm(false);
  }

}
