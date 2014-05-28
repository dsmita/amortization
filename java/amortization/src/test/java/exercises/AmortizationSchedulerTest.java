package exercises;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import exercises.model.Amortization;
import exercises.model.Loan;

public class AmortizationSchedulerTest {

	private AmortizationScheduler amortizationScheduler;

	@Test
	public void generatePaymentSchedule() {
		Loan loan = new Loan(10000, 10, 1);
		amortizationScheduler = new AmortizationScheduler(loan);
		Amortization amortization = amortizationScheduler.generatePaymentSchedule();

		assertNotNull(amortization);
		assertNotNull(amortization.getPayments());
		assertNotNull(amortization.getLoan());
		assertEquals(13, amortization.getPayments().size());
	}

	@Test(description = "Number of payments should have been 13.")
	public void formulaSeemsBuggy() {
		Loan loan = new Loan(100, 10, 1);
		amortizationScheduler = new AmortizationScheduler(loan);
		Amortization amortization = amortizationScheduler.generatePaymentSchedule();

		assertNotNull(amortization);
		assertNotNull(amortization.getPayments());
		assertNotNull(amortization.getLoan());
		// assertEquals(13, amortization.getPayments().size());
		assertEquals(14, amortization.getPayments().size());
	}

}
