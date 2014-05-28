package exercises;

import java.io.IOException;
import java.util.List;

import exercises.io.ConsoleIOChannel;
import exercises.io.IOChannel;
import exercises.model.Amortization;
import exercises.model.Loan;
import exercises.model.Payment;

public class Main {

	private static final String HEADER_FORMAT = "%1$-20s%2$-20s%3$-20s%4$s,%5$s,%6$s\n";
	private static final String PAYMENT_FORMAT = "%1$-20d%2$-20.2f%3$-20.2f%4$.2f,%5$.2f,%6$.2f\n";

	public static void main(String[] args) {

		IOChannel channel = new ConsoleIOChannel();

		LoanConsole console = new LoanConsole();
		console.setChannel(channel);

		try {
			Loan loan = console.newLoanApplication();
			AmortizationScheduler scheduler = new AmortizationScheduler(loan);
			Amortization amortization = scheduler.generatePaymentSchedule();

			List<Payment> payments = amortization.getPayments();

			channel.write(HEADER_FORMAT, "PaymentNumber", "PaymentAmount", "PaymentInterest", "CurrentBalance", "TotalPayments", "TotalInterestPaid");

			for (Payment payment : payments) {
				channel.write(PAYMENT_FORMAT, payment.getNumber(), payment.getAmount(), payment.getInterest(), payment.getCurrentBalance(),
						payment.getTotalPayments(), payment.getTotalInterestPaid());
			}

		} catch (IllegalArgumentException e) {
			channel.write("Unable to process the values entered. Terminating program.\n");
		} catch (IOException e) {
			channel.write("An IOException was encountered. Terminating program.\n");
		}
	}
}
