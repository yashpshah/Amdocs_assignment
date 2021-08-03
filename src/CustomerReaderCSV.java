import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CustomerReaderCSV implements CustomerReader {
	@Override
	public Customer read(String line) throws IncorrectCustomerFormatException {
		String split[] = line.split(",");
		if(split.length<2) {
			throw new IncorrectCustomerFormatException();
		}
		Customer customer = new Customer();
		customer.setId(Long.parseLong(split[0]));
		for(char c:split[1].toCharArray()) {
			//Only alphanumeric are allowed
			if(!Character.isAlphabetic(c) && !Character.isDigit(c) && c!=' ') {
				throw new IncorrectCustomerFormatException();
			}
		}
		customer.setName(split[1]);
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if(split.length>2 && split[2]!=null && split[2].length()!=0) {
			Date date = formatter.parse(split[2]);
			String formattedDate = targetFormat.format(date);
			customer.setDate(formattedDate);
			}
		} catch (ParseException e) {
			throw new IncorrectCustomerFormatException();
		}
		return customer;
	}

}
