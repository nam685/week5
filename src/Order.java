import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class Order {
	static List<LocalDate> HOLIDAYS = Arrays.asList(
			LocalDate.of(2020, 3, 8),
			LocalDate.of(2020, 3, 25),
			LocalDate.of(2020, 3, 30),
			LocalDate.of(2020, 3, 31)
	);
	
	long orderId;
	LocalDate pick, firstAttempt, secondAttempt;
	City buyerCity, sellerCity;
	
	public Order(String rowString) {
		String[] row = rowString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		if (row.length != 6) {
			throw new AssertionError();
		}
		
		orderId = Long.parseLong(row[0]);
		pick = LocalDate.ofInstant(Instant.ofEpochSecond(Long.parseLong(row[1])), ZoneId.of("GMT+8"));
		firstAttempt = LocalDate.ofInstant(Instant.ofEpochSecond((long)Double.parseDouble(row[2])), ZoneId.of("GMT+8"));
		if (row[3] != null && !row[3].isEmpty()) {
			secondAttempt = LocalDate.ofInstant(Instant.ofEpochSecond((long)Double.parseDouble(row[3])), ZoneId.of("GMT+8"));
		}
		
		String buyerAddress = row[4].toLowerCase();
		String sellerAddress = row[5].toLowerCase();
		
		buyerAddress = buyerAddress.startsWith("\"") ? buyerAddress.substring(1) : buyerAddress;
		sellerAddress = sellerAddress.startsWith("\"") ? sellerAddress.substring(1) : sellerAddress;
		
		buyerAddress = buyerAddress.endsWith("\"") ? buyerAddress.substring(0, buyerAddress.length() - 1) : buyerAddress;
		sellerAddress = sellerAddress.endsWith("\"") ? sellerAddress.substring(0, sellerAddress.length() - 1) : sellerAddress;
		
		buyerAddress = buyerAddress.strip();
		sellerAddress = sellerAddress.strip();
		
		if (buyerAddress.endsWith("metro manila")) {
			buyerCity = City.MetroManila;
		} else if (buyerAddress.endsWith("luzon")) {
			buyerCity = City.Luzon;
		} else if (buyerAddress.endsWith("visayas")) {
			buyerCity = City.Visayas;
		} else if (buyerAddress.endsWith("mindanao")){
			buyerCity = City.Mindanao;
		} else {
			throw new AssertionError(buyerAddress, null);
		}
		
		if (sellerAddress.endsWith("metro manila")) {
			sellerCity = City.MetroManila;
		} else if (sellerAddress.endsWith("luzon")) {
			sellerCity = City.Luzon;
		} else if (sellerAddress.endsWith("visayas")) {
			sellerCity = City.Visayas;
		} else if (sellerAddress.endsWith("mindanao")) {
			sellerCity = City.Mindanao;
		} else {
			throw new AssertionError(sellerAddress, null);
		}
		if (pick.getYear() != 2020 || firstAttempt.getYear() != 2020 || (secondAttempt != null && secondAttempt.getYear() != 2020)) {
			throw new AssertionError();
		}
		if (!workDate(pick) || !workDate(firstAttempt) || (secondAttempt != null && !workDate(secondAttempt))) {
			throw new AssertionError();
		}
	}
	
	static boolean workDate(LocalDate date) {
		return !HOLIDAYS.contains(date) && !date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
	}
	
	static int getDays(City sellerCity, City buyerCity) {
		if (sellerCity == City.MetroManila && buyerCity == City.MetroManila) {
			return 3;
		} else if (sellerCity == City.MetroManila && buyerCity == City.Luzon ||
				sellerCity == City.Luzon && buyerCity == City.MetroManila ||
				sellerCity == City.Luzon && buyerCity == City.Luzon) {
			return 5;
		} else {
			return 7;
		}
	}
	
	static LocalDate incrementDay(LocalDate day) {
		LocalDate result = null;
		LocalDate temp = day;
		while (true) {
			temp = temp.plusDays(1);
			if (workDate(temp)) {
				result = temp;
				break;
			}
		}
		return result;
	}
	
	public boolean check() {
		int SLA = getDays(sellerCity, buyerCity);
		boolean firstAttemptOnTime = false;
		LocalDate temp = this.pick;
		for (int day = 1; day <= SLA; day++) {
			temp = incrementDay(temp);
			if (temp.equals(this.firstAttempt)) {
				firstAttemptOnTime = true;
				break;
			}
		}
		if (!firstAttemptOnTime) {
			return false;
		}
		if (this.secondAttempt == null) {
			return true;
		}
		boolean secondAttemptOnTime = false;
		temp = this.firstAttempt;
		for (int day = 1; day <= 3; day++) {
			temp = incrementDay(temp);
			if (temp.equals(secondAttempt)) {
				secondAttemptOnTime = true;
				break;
			}
		}
		return secondAttemptOnTime;
	}

	public long orderId() {
		return orderId;
	}

	public LocalDate pick() {
		return pick;
	}

	public LocalDate firstAttempt() {
		return firstAttempt;
	}

	public LocalDate secondAttempt() {
		return secondAttempt;
	}

	public City buyerCity() {
		return buyerCity;
	}

	public City sellerCity() {
		return sellerCity;
	}
	
	public String toString() {
		return "" + orderId + "\t" + pick + "\t" + firstAttempt + "\t" + secondAttempt + "\t" + buyerCity + "\t" + sellerCity;
	}
}
