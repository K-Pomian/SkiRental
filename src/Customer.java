import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import enums.Activity;

public class Customer implements Runnable {
	
	private final EquipmentRental equipmentRental;
	
	private boolean needsSkis;
	private boolean needsBoots;
	private boolean needsHelmet;
	private boolean sleigh;
	private boolean walk;
	
	public Customer(EquipmentRental equipmentRental) {
		while (true) {
			this.needsSkis = Math.round(Math.random()) == 1;
			this.needsBoots = Math.round(Math.random()) == 1;
			this.needsHelmet = Math.round(Math.random()) == 1;
			
			if ((this.needsSkis || this.needsBoots || this.needsHelmet) == true) {
				break;
			}
		}
		
		this.equipmentRental = equipmentRental;
		this.sleigh = Math.round(Math.random()) == 1;
		this.walk = Math.round(Math.random()) == 1;
	}

	@Override
	public void run() {
		List<Activity> activities = Arrays.asList(Activity.values());
		Collections.shuffle(activities);
		activities.forEach(activity -> {
			switch (activity) {
			case SLEIGH: 
				if (this.sleigh) {
					equipmentRental.goSleighing();
				}
				break;
			case WALK:
				if (this.walk) {
					equipmentRental.goWalking();
				}
				break;
			case BORROW:
				equipmentRental.borrowEquipment(needsSkis, needsBoots, needsHelmet);
				break;
			}
		});
	}
	
}
