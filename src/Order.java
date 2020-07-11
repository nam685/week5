import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Order {
	Long orderId;
	LocalDate pick;
	LocalDate firstDeliverAttempt;
	LocalDate secondDeliverAttempt;
	City buyerCity;
	City sellerCity;
	long firstDelay = -1;
	long secondDelay = -1;
	long SLA = -1;
	boolean late;
	
	public Order(String rowString) {
		String[] row = rowString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		
		orderId = Long.parseLong(row[0]);
		pick = LocalDate.ofInstant(Instant.ofEpochSecond(Long.parseLong(row[1])), ZoneId.of("GMT+8"));
		firstDeliverAttempt = LocalDate.ofInstant(Instant.ofEpochSecond((long)Double.parseDouble(row[2])), ZoneId.of("GMT+8"));
		if (!row[3].isEmpty()) {
			secondDeliverAttempt = LocalDate.ofInstant(Instant.ofEpochSecond((long)Double.parseDouble(row[3])), ZoneId.of("GMT+8"));
		}
		
		String buyerAddress = row[4].toLowerCase();
		String sellerAddress = row[5].toLowerCase();
		
		buyerAddress = buyerAddress.startsWith("\"") ? buyerAddress.substring(1) : buyerAddress;
		sellerAddress = sellerAddress.startsWith("\"") ? sellerAddress.substring(1) : sellerAddress;
		
		buyerAddress = buyerAddress.endsWith("\"") ? buyerAddress.substring(0, buyerAddress.length() - 1) : buyerAddress;
		sellerAddress = sellerAddress.endsWith("\"") ? sellerAddress.substring(0, sellerAddress.length() - 1) : sellerAddress;
		
		if (buyerAddress.endsWith("metro manila")) {
			buyerCity = City.MetroManila;
		} else if (buyerAddress.endsWith("luzon")) {
			buyerCity = City.Luzon;
		} else if (buyerAddress.endsWith("visayas")) {
			buyerCity = City.Visayas;
		} else {
			buyerCity = City.Mindanao;
		}
		
		if (sellerAddress.endsWith("metro manila")) {
			sellerCity = City.MetroManila;
		} else if (sellerAddress.endsWith("luzon")) {
			sellerCity = City.Luzon;
		} else if (sellerAddress.endsWith("visayas")) {
			sellerCity = City.Visayas;
		} else {
			sellerCity = City.Mindanao;
		}
		
		if (buyerCity == City.Visayas || buyerCity == City.Mindanao || sellerCity == City.Visayas || sellerCity == City.Mindanao) {
			SLA = 7;
		} else if (buyerCity == City.Luzon || sellerCity == City.Luzon) {
			SLA = 5;
		} else {
			SLA = 3;
		}
		
		late = examine();
	}

	private boolean examine() {
		firstDelay = ChronoUnit.DAYS.between(pick, firstDeliverAttempt);
		if (secondDeliverAttempt != null) {
			secondDelay = ChronoUnit.DAYS.between(firstDeliverAttempt, secondDeliverAttempt);
		}
		if (secondDelay == -1) {
			return firstDelay <= SLA;
		} else {
			return firstDelay <= SLA && secondDelay <= 3;
		}
	}

	public Long getOrderId() {
		return orderId;
	}

	public LocalDate getPick() {
		return pick;
	}

	public LocalDate getFirstDeliverAttempt() {
		return firstDeliverAttempt;
	}

	public LocalDate getSecondDeliverAttempt() {
		return secondDeliverAttempt;
	}

	public City getBuyerCity() {
		return buyerCity;
	}

	public City getSellerCity() {
		return sellerCity;
	}
	
	public String toString() {
		return "OrderID " + orderId + " Pick "+ pick + " FirstAttempt "+ firstDeliverAttempt + " SecondAttempt "+ secondDeliverAttempt + " BuyerCity "+ buyerCity + " SellerCity "+ sellerCity + " SLA " + SLA + " FirstDelay " + firstDelay + " SecondDelay " + secondDelay + " Is in time = " + isLate();
	}

	public long getSLA() {
		return SLA;
	}

	public boolean isLate() {
		return late;
	}
	
	
}
