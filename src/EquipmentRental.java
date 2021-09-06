import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import enums.Activity;
import enums.Item;

public class EquipmentRental {
	
	private final Semaphore skisSemaphore;
	private final Semaphore bootsSemaphore;
	private final Semaphore helmetsSemaphore;
	private final Semaphore sleighSemaphore;
	
	public EquipmentRental(int skis, int boots, int helmets) {
		skisSemaphore = new Semaphore(skis, true);
		bootsSemaphore = new Semaphore(boots, true);
		helmetsSemaphore = new Semaphore(helmets, true);
		sleighSemaphore = new Semaphore(2, true);
	}
	
	public void borrowEquipment(boolean skis, boolean boots, boolean helmet) {
		printBorrowRequest(skis, boots, helmet);
		
		long borrowTime = (long) (8 + Math.random() * 12);
		
		
		try {
			if (skis) {
				if (skisSemaphore.availablePermits() == 0) {
					printWaitForItem(Item.SKIS);
				}
				skisSemaphore.acquire();
				printTakeItem(Item.SKIS);
				printAvailableItems();
			}
			if (boots) {
				if (bootsSemaphore.availablePermits() == 0) {
					printWaitForItem(Item.BOOTS);
				}
				bootsSemaphore.acquire();
				printTakeItem(Item.BOOTS);
				printAvailableItems();
			}
			if (helmet) {
				if (helmetsSemaphore.availablePermits() == 0) {
					printWaitForItem(Item.HELMET);
				}
				helmetsSemaphore.acquire();
				printTakeItem(Item.HELMET);
				printAvailableItems();
			}
			
			printBorrow(skis, boots, helmet, borrowTime);
			printAvailableItems();
			
			TimeUnit.SECONDS.sleep(borrowTime);
		} catch (InterruptedException e) {
			e.getStackTrace();
		} finally {
			printReturn(skis, boots, helmet);
			printAvailableItemsAfterReturn(skis, boots, helmet);
			if (skis) {
				skisSemaphore.release();
			}
			if (boots) {
				bootsSemaphore.release();
			}
			if (helmet) {
				helmetsSemaphore.release();
			}
		}
	}
	
	public void goSleighing() {
		long sleighingTime = (long) (5 + Math.random() * 10);
		try {
			if (sleighSemaphore.availablePermits() == 0) {
				printWaitForActivity(Activity.SLEIGH);
			}
			printDoActiivity(Activity.SLEIGH, sleighingTime);
			sleighSemaphore.acquire();
			TimeUnit.SECONDS.sleep(sleighingTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			printFinishActivity(Activity.SLEIGH);
			sleighSemaphore.release();
		}
	}
	
	public void goWalking() {
		long walkingTime = (long) (5 + Math.random() * 15);
		printDoActiivity(Activity.WALK, walkingTime);
		try {
			TimeUnit.SECONDS.sleep(walkingTime);
			printFinishActivity(Activity.WALK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void printAvailableItems() {
		System.out.println("\tAvailable items: (" + skisSemaphore.availablePermits() + "," + bootsSemaphore.availablePermits() + "," + helmetsSemaphore.availablePermits() + ").");
	}
	
	private void printAvailableItemsAfterReturn(boolean skis, boolean boots, boolean helmet) {
		System.out.println("\tAvailable items: (" 
				+ (skisSemaphore.availablePermits() + Boolean.compare(skis, false)) + "," 
				+ (bootsSemaphore.availablePermits() + Boolean.compare(boots, false)) + "," 
				+ (helmetsSemaphore.availablePermits() + Boolean.compare(helmet, false)) + ").");
	}
	
	private void printDoActiivity(Activity activity, long time) {
		String activityName;
		switch (activity) {
			case SLEIGH:
				activityName = "sleighing";
				break;
			case WALK:
				activityName = "walking";
				break;
			default:
				return;
		}
		System.out.println(Thread.currentThread().getName() + " goes " + activityName + " for " + time + " seconds.");
	}
	
	private void printWaitForActivity(Activity activity) {
		String activityName;
		switch (activity) {
		case SLEIGH:
			activityName = "sleigh";
			break;
		default:
			return;
		}
		System.out.println(Thread.currentThread().getName() + " waits for " + activityName);
	}
	
	private void printFinishActivity(Activity activity) {
		String activityName;
		switch (activity) {
		case SLEIGH:
			activityName = "sleighing";
			break;
		case WALK:
			activityName = "walking";
			break;
		default:
			return;
		}
		System.out.println(Thread.currentThread().getName() + " finished " + activityName);
	}
	
	private void printBorrowRequest(boolean skis, boolean boots, boolean helmet) {
		System.out.println(Thread.currentThread().getName() + " wants to borrow ("
				+ Boolean.compare(skis, false) + "," + Boolean.compare(boots, false) + "," + Boolean.compare(helmet, false)
				+ ") items.");
	}
	
	private void printWaitForItem(Item item) {
		String itemName;
		switch (item) {
			case SKIS:
				itemName = "skis";
				break;
			case BOOTS:
				itemName = "boots";
				break;
			case HELMET:
				itemName = "helmet";
				break;
			default:
				return;
		}
		System.out.println(Thread.currentThread().getName() + " waits for " + itemName + ".");
	}
	
	private void printTakeItem(Item item) {
		String itemName;
		switch (item) {
			case SKIS:
				itemName = "skis";
				break;
			case BOOTS:
				itemName = "boots";
				break;
			case HELMET:
				itemName = "helmet";
				break;
			default:
				return;
		}
		System.out.println(Thread.currentThread().getName() + " takes " + itemName + ".");
	}
	
	private void printBorrow(boolean skis, boolean boots, boolean helmet, long time) {
		System.out.println(Thread.currentThread().getName() + " borrows ("
				+ Boolean.compare(skis, false) + "," + Boolean.compare(boots, false) + "," + Boolean.compare(helmet, false)
				+ ") items for " + time + " seconds.");
	}
	
	private void printReturn(boolean skis, boolean boots, boolean helmet) {
		System.out.println(Thread.currentThread().getName() + " returns ("
				+ Boolean.compare(skis, false) + "," + Boolean.compare(boots, false) + "," + Boolean.compare(helmet, false)
				+ ") items.");
	}
}
