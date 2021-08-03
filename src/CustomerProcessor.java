import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerProcessor {
	public static void main(String args[]) throws IOException {
		System.getProperty("user.dir");
		args = new String[1];
		args[0] = "Customers.csv";
		String split[] = args[0].split("\\.");
		String extension = split[1];
		CustomerReader reader = null;
		List<Customer> list = new ArrayList<>();
		int count = 0;
		final long interval = 20 ;
		long lastLogged = System.currentTimeMillis();
		File writeToFile = new File(System.getProperty("user.dir")+"//finalcustomer.txt");
		FileWriter fw=new FileWriter(writeToFile,true);
		
		if(extension.equalsIgnoreCase("csv")) {
			reader = new CustomerReaderCSV();
		}else{
			reader = new CustomerReaderText();
		}
		if(reader!=null) {
			FileReader fr= new FileReader(new File(System.getProperty("user.dir")+"//"+args[0]));
			BufferedReader br= new BufferedReader(fr);
			String line = null;
			boolean first = true;
			while((line=br.readLine())!=null){
				//assuming that ordering of headers doesn't change, so we skip reading the headers	
				if(first) {
						first = false;
						continue;
					}
				 //If 20ms constraint gets satisfied	
				if (System.currentTimeMillis() - lastLogged > interval) {
					    lastLogged = System.currentTimeMillis();
					    System.out.println("Current count:"+count);
					    if(count>=1) {
					    	System.out.println(list.get(list.size()-1).date);
					    }
					}
					try {
						list.add(reader.read(line));
						count++;
						fw.write(list.get(list.size()-1).toString());
						fw.write("\n");
						
					} catch (IncorrectCustomerFormatException e) {
						fw.write("EXP\n");
						continue;
					}
				}
			
			System.out.println("Total count" + count);
			br.close();
			fw.flush();
	        fw.close();
		}
	}
}

