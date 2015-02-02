package aqu.ccservice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class facade for the Credit card service:
 * reads a csv file to fetch the credit card information (bank name, cc number, expiry date) and
 * sorts by expiry in descending order 
 * 
 * The service adds to the list of credi cards the Credit Cards objects returned by the Builder.
 * In case of an Exception the line number of the CSV file is added to the list fileLineErrors.
 * 
 * @author aqu
 */
public class CreditCardService {

	private List<CreditCard> creditCardList;
	private List<Integer> fileLineErrors;
	
	public CreditCardService() {
		creditCardList = new ArrayList<CreditCard>();
	}

	public List<CreditCard> readCsvFile(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		List<String> fileLines = Files.readAllLines(path, StandardCharsets.UTF_8);
		List<CreditCard> creditCardList = new ArrayList<CreditCard>();
		int cntLine = 0;
		for (String fileLine: fileLines) {
			String[] creditCardTokens = fileLine.split(",");
			CreditCard card = null;
			
			try {
				card = new CreditCard.Builder().setBankName(creditCardTokens[0].trim())
						.setCreditCardNumber(creditCardTokens[1].trim())
						.setExpiryDate(creditCardTokens[2].trim())
						.build();
				creditCardList.add(card);
				
			} catch (CreditCardCreationException e) {
				fileLineErrors.add(cntLine);
				e.printStackTrace();
			}
			
			cntLine ++;
		}
		Collections.sort(creditCardList, new CreditCardComparator());
		return creditCardList;
	}
	
	public List<CreditCard> getCreditCardList() {
		return creditCardList;
	}

	public List<Integer> getFileLineErrors() {
		return fileLineErrors;
	}

	public class CreditCardComparator implements Comparator<CreditCard> {

		@Override
		public int compare(CreditCard o1, CreditCard o2) {
			return o2.getExpiryDate().compareTo(o1.getExpiryDate());

		}
		
	}
	
	public static void main(String[] args) {
		try {
			List<CreditCard> list = new CreditCardService().readCsvFile("/Users/Marie/Documents/workspace/CreditCardService/src/test/resources/creditcards.csv");
			for (CreditCard cc: list) {
				System.out.println(cc.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
