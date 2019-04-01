package pmm.gamble.gambling;

import java.util.UUID;

public class Gambler {
	UUID uuid;
	int amount;
	
	public Gambler(UUID uuid){
		this.uuid = uuid;
		this.amount = 100;
	}

	public Gambler(UUID uuid, int newAmount) {
		this.uuid = uuid;
		this.amount = newAmount;
	}
}
