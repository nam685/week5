import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		
		String pathToCsv = "./data/delivery_orders_march.csv";
		String pathToSampleCsv = "./data/delivery_orders_march_sample.csv";
		String outputFile = "./data/output.csv";
		
		BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
		FileWriter sampleCsvWriter = new FileWriter(pathToSampleCsv);
		String rowString;

		csvReader.readLine(); // throw the header away
		
		int count = 0;
		while ((rowString = csvReader.readLine()) != null && count < 10) {
			count++;
			sampleCsvWriter.append(rowString);
			sampleCsvWriter.append("\n");
		    // String[] data = row.split(",");
		    // do something with the data
		}
		
		sampleCsvWriter.flush();
		sampleCsvWriter.close();
		
		csvReader.close();

		BufferedReader sampleCsvReader = new BufferedReader(new FileReader(pathToCsv));
		sampleCsvReader.readLine();
		
		
		List<Order> orders = new ArrayList<>();
		
		while ((rowString = sampleCsvReader.readLine()) != null) {
		    orders.add(new Order(rowString));   
		}
		sampleCsvReader.close();
		
		StringBuilder outputBuilder = new StringBuilder();
		outputBuilder.append("orderid,is_late").append("\n");
		for (Order order : orders) {
			outputBuilder.append(order.orderId + ",");
			if (order.check()) {
				outputBuilder.append("0\n");
			} else {
				outputBuilder.append("1\n");
			}
		}
		
		PrintStream output = new PrintStream(outputFile);
		output.append(outputBuilder.toString());
		output.close();
	}
}
