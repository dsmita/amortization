package exercises;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;


public class LoanValidatorTest {

  private LoanValidator validator = new LoanValidator();

  @Test
  public void minimumBorrowAmount() {
    assertTrue(validator.isValidBorrowAmount(0.01));
    assertFalse(validator.isValidBorrowAmount(0.001));
  }

  @Test
  public void maxBorrowAmount() {
    assertTrue(validator.isValidBorrowAmount(1000000000000d));
    assertFalse(validator.isValidBorrowAmount(1000000000001d));
  }

  @Test
  public void minimumAnualInterestRate() {
    assertTrue(validator.isValidAnnualInterest(0.000001));
    assertFalse(validator.isValidAnnualInterest(0.0000001));
  }

  @Test
  public void maxAnualInterestRate() {
    assertTrue(validator.isValidAnnualInterest(100));
    assertFalse(validator.isValidAnnualInterest(100.1));
  }

  @Test
  public void minimumLoanTermIsOneYear() {
    assertTrue(validator.isValidTerm(1));
    assertFalse(validator.isValidTerm(0));
  }

  @Test
  public void maxLoanTermIs1000000() {
    assertTrue(validator.isValidTerm(1000000));
    assertFalse(validator.isValidTerm(1000001));
  }

}
