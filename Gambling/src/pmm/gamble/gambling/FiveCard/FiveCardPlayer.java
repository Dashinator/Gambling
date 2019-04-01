package pmm.gamble.gambling.FiveCard;

import java.util.ArrayList;
import java.util.UUID;

import pmm.gamble.gambling.Utils.Card;

public class FiveCardPlayer {
	int amount;
	UUID uuid;
	ArrayList<Card> hand;
	
	public FiveCardPlayer(UUID uuid, int amount)
	{
		this.uuid = uuid;
		this.amount = amount;
	}
	
	public void givehand(ArrayList<Card> hand) {
		this.hand = hand;
	}
}
