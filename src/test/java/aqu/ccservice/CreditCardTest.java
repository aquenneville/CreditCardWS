package aqu.ccservice;

import static org.junit.Assert.*;

import org.junit.Test;

import aqu.ccservice.CreditCard;
import aqu.ccservice.CreditCardCreationException;

public class CreditCardTest {

	@Test
	public void testGetBankName() {
		CreditCard card = new CreditCard.Builder().setBankName("HSBC Canada")
				.build();
		assertEquals(card.getBankName(), "HSBC Canada");
	}

	@Test
	public void testGetCreditCardNumber() {
		CreditCard card = null;
		try {
			card = new CreditCard.Builder().setCreditCardNumber(
					"4569-4432-2432-4321").build();
		} catch (CreditCardCreationException exc) {
			fail("Creation problem - number is valid!");
		}
		assertEquals(card.getCreditCardNumber(), "4569-4432-2432-4321");

		try {
			card = new CreditCard.Builder().setCreditCardNumber(
					"4569-4432-2432-ABJF").build();
		} catch (CreditCardCreationException exc) {
			assertTrue(exc.getMessage().length() > 0);
		}

		try {
			card = new CreditCard.Builder().setCreditCardNumber(
					"4569-4432-A^FDGSj").build();
		} catch (CreditCardCreationException exc) {
			assertTrue(exc.getMessage().length() > 0);
		}
	}

	@Test
	public void testHideCreditCardNumber() {
		CreditCard card = null;
		try {
			card = new CreditCard.Builder().setCreditCardNumber(
					"4569-4432-2432-4321").build();
		} catch (CreditCardCreationException exc) {
			fail(exc.getMessage());
		}
		assertEquals(card.hideCreditCardNumber(), "4569-XXXX-XXXX-XXXX");
	}

	@Test
	public void testGetExpiryDate() {
		CreditCard card = null;
		try {
			card = new CreditCard.Builder().setExpiryDate("Apr-2018").build();
		} catch (CreditCardCreationException exc) {
			fail(exc.getMessage());
		}
		assertEquals(card.getExpiryDateStringFormatted(), "Apr-2018");

		try {
			card = new CreditCard.Builder().setExpiryDate("XXX-2018").build();
		} catch (CreditCardCreationException exc) {
			assertTrue(exc.getMessage().length() > 0);
		}

	}

	@Test
	public void testCreditCard() {
		CreditCard card = null;

		try {
			card = new CreditCard.Builder().setBankName("American Express")
					.setCreditCardNumber("5149-4321-4329-324")
					.setExpiryDate("May-2017").build();
		} catch (CreditCardCreationException e) {
			fail(e.getMessage());
		}
		assertTrue(card != null);
		assertEquals(card.getBankName(), "American Express");
		assertEquals(card.getCreditCardNumber(), "5149-4321-4329-324");
		assertEquals(card.getExpiryDateStringFormatted(), "May-2017");

	}

}
