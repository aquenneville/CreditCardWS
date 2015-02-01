package aqu.ccservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Immutable class representation of a Credit card. 
 * 
 * @author 
 * aqu
 *
 */
public class CreditCard {
	
	private String bankName;
	
	private int[] creditCardNumber;
	
	private DateTime expiryDate;
	
	private CreditCard() {
	}

	private CreditCard(Builder ccb) {
		this.bankName = ccb.bankName;
		this.creditCardNumber = ccb.creditCardNumber;
		this.expiryDate = ccb.expiryDate;
	}
	
	public String getBankName() {
		return bankName;
	}

	public String getCreditCardNumber() {
		StringBuilder stb = new StringBuilder();
		if (creditCardNumber == null) {
			creditCardNumber = new int[4];
		}
		for (int i = 0; i < creditCardNumber.length; i ++) {
			
			stb.append(creditCardNumber[i]); 
			if (i < 3) {
				stb.append("-");
			}
		}
		return stb.toString();
	}
	
	private String hideNumber(String strToHide, char useChar) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < strToHide.length(); i ++) {
			builder.append(useChar);
		}
		return builder.toString();
	}
	
	public String hideCreditCardNumber() {
		StringBuilder stb = new StringBuilder();
		for (int i = 0; i < creditCardNumber.length; i ++) {
			
			if (i > 0) {
				stb.append(hideNumber(""+creditCardNumber[i], 'X'));
			}
			if (i == 0) {
				stb.append(creditCardNumber[i]);
			}
			if (i < 3) {
				stb.append("-");
			}
		}
		return stb.toString();
	}

	public DateTime getExpiryDate() {
		return expiryDate;
	}
	
	public String getExpiryDateStringFormatted() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM-yyyy");
		return formatter.print(expiryDate);
	}

	public static class Builder {
		private String bankName;
		private int[] creditCardNumber;
		private DateTime expiryDate;
		
		public Builder setBankName(String aBankName) {
			bankName = aBankName;
			return this;			
		}
		
		public Builder setCreditCardNumber(String ccNumber) throws CreditCardCreationException {
			String[] ccNumberTokens = ccNumber.split("-");
			creditCardNumber = new int[4];
			
			if (ccNumberTokens.length < 4) {
				throw new CreditCardCreationException("Credit card creation failed. Invalid credit card number expecting number to be splitted by a dash (-). i.e. XXXX-XXXX-XXXX-XXXX");
			} 
			
			// check if tokens are numbers
			int cnt = 0;
			for (String ccToken: ccNumberTokens) {
				try {
					creditCardNumber[cnt] = Integer.parseInt(ccToken); 
				} catch(NumberFormatException exc) {
					throw new CreditCardCreationException("Credit card creation failed! Invalid credit card number expecting all digits to be numbers or dash.");
				}
				cnt ++;
			}
			
			return this;
		}
		
		public Builder setExpiryDate(String ccExpiryDate) throws CreditCardCreationException {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("MMM-yyyy");
			
			try {
				expiryDate = formatter.parseDateTime(ccExpiryDate);
			} catch (IllegalArgumentException exc) {
				throw new CreditCardCreationException("Credit card creation failed! Invalid credit card expiry date expecting date to be of the following format: MMM-yyyy. i.e. Jan-2018");
			}
			
			return this;
		}
		
		public CreditCard build() {
			return new CreditCard(this);
		}
		
	}
	
	public String toString() {
		return new StringBuilder()
				.append(this.getBankName()).append(" ")
				.append(this.hideCreditCardNumber()).append(" ")
				.append(this.getExpiryDateStringFormatted())
				.toString();
	}
	
	public static void main(String[] args) throws CreditCardCreationException {
		CreditCard cc = new CreditCard.Builder().setBankName("").setCreditCardNumber("").setExpiryDate("").build();
	}
}
