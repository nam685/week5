import java.sql.Timestamp;

public class Order {
	Long orderId;
	Timestamp pick;
	Timestamp firstDeliverAttempt;
	Timestamp secondDeliverAttempt;
	City buyerCity;
	City sellerCity;
	
	public Order(String rowString) {
		String[] row = rowString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		
		orderId = Long.parseLong(row[0]);
		pick = new Timestamp(Long.parseLong(row[1]));
		firstDeliverAttempt = new Timestamp((long)Double.parseDouble(row[2]));
		if (!row[3].isEmpty()) {
			secondDeliverAttempt = new Timestamp((long)Double.parseDouble(row[3]));
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
	}

	public Long getOrderId() {
		return orderId;
	}

	public Timestamp getPick() {
		return pick;
	}

	public Timestamp getFirstDeliverAttempt() {
		return firstDeliverAttempt;
	}

	public Timestamp getSecondDeliverAttempt() {
		return secondDeliverAttempt;
	}

	public City getBuyerCity() {
		return buyerCity;
	}

	public City getSellerCity() {
		return sellerCity;
	}
	
	public String toString() {
		return "" + orderId + "   "+ pick + "   "+ firstDeliverAttempt + "   "+ secondDeliverAttempt + "   "+ buyerCity + "   "+ sellerCity;
	}
}
