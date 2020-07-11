import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		
		String pathToCsv = "C:\\Users\\Nam Le\\Shopee_Code_League\\Week5\\delivery_orders_march.csv";
		String javaPathToCsv = pathToCsv.replace("\\", "/");
		String pathToSampleCsv = "C:\\Users\\Nam Le\\Shopee_Code_League\\Week5\\delivery_orders_march_sample.csv";
		String javaPathToSampleCsv = pathToSampleCsv.replace("\\", "/");
		
		BufferedReader csvReader = new BufferedReader(new FileReader(javaPathToCsv));
		FileWriter sampleCsvWriter = new FileWriter(javaPathToSampleCsv);
		String row;

		String header = csvReader.readLine(); // throw this away
		
		int count = 0;
		while ((row = csvReader.readLine()) != null && count < 10) {
			count++;
			sampleCsvWriter.append(row);
			sampleCsvWriter.append("\n");
		    // String[] data = row.split(",");
		    // do something with the data
		}
		
		sampleCsvWriter.flush();
		sampleCsvWriter.close();
		
		csvReader.close();

		BufferedReader sampleCsvReader = new BufferedReader(new FileReader(javaPathToSampleCsv));
		
		List<Order> orders = new ArrayList<>();
		
		while ((row = sampleCsvReader.readLine()) != null) {
		    String[] data = row.split(",");
		    // do something with the data
		    orders.add(new Order(data));
		    
		}
		sampleCsvReader.close();
		
		for (Order o : orders) {
			System.out.println(o.toString());
		}
	}
}
