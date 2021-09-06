import java.util.concurrent.TimeUnit;

public class Launcher {
	
	public static void main(String[] args) {
		final int threadNumber = 10;
		EquipmentRental equipmentRental = new EquipmentRental(2, 2, 2);
		
		Thread customers[] = new Thread[threadNumber];
		for (int i = 1; i <= threadNumber; i++) {
			Thread customer = new Thread(new Customer(equipmentRental));
			customer.setName("Customer-" + i);
			customers[i-1] = customer;
		}
		
		for (Thread customer : customers) {
			customer.start();
			try {
				long timeToNextCustomer = 1 + Math.round(Math.random() * 2);
				TimeUnit.SECONDS.sleep(timeToNextCustomer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
