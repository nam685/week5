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
		
		String pathToSubmissionCsv = "C:\\Users\\Nam Le\\Shopee_Code_League\\Week5\\submission.csv";
		String javaPathToSubmissionCsv = pathToSubmissionCsv.replace("\\", "/");
		String pathToSampleSubmissionCsv = "C:\\Users\\Nam Le\\Shopee_Code_League\\Week5\\sample_submission.csv";
		String javaPathToSampleSubmissionCsv = pathToSampleSubmissionCsv.replace("\\", "/");
		
		BufferedReader csvReader = new BufferedReader(new FileReader(javaPathToCsv));
		FileWriter sampleCsvWriter = new FileWriter(javaPathToSampleCsv);
		String rowString;

		String header = csvReader.readLine(); // throw this away
		
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

		BufferedReader sampleCsvReader = new BufferedReader(new FileReader(javaPathToSampleCsv));
		
		List<Order> orders = new ArrayList<>();
		
		while ((rowString = sampleCsvReader.readLine()) != null) {
		    orders.add(new Order(rowString));
		    
		}
		sampleCsvReader.close();
		
		
		FileWriter sampleSubmissionCsvWriter = new FileWriter(javaPathToSampleSubmissionCsv);
		sampleSubmissionCsvWriter.append("orderid,islate\n");

		for (Order o : orders) {
			System.out.println(o.toString());
			sampleSubmissionCsvWriter.append(o.getOrderId().toString());
			sampleSubmissionCsvWriter.append(",");
			sampleSubmissionCsvWriter.append(o.isLate() ? "1" : "0");
			sampleSubmissionCsvWriter.append("\n");
			
		}
		
		sampleSubmissionCsvWriter.flush();
		sampleSubmissionCsvWriter.close();
	}
}
