package pmm.gamble.gambling.FiveCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pmm.gamble.gambling.GamblingController;
import pmm.gamble.gambling.Utils.Card;
import pmm.gamble.gambling.Utils.Suits;

public class FiveCardController {
	private enum States{
		Empty,
		WaitingForPlayers,
		DealingCards,
		Paying
	}
	
	private static FiveCardController fiveCard_instance = null;
	
	public static FiveCardController getInstance() {
		if(fiveCard_instance == null) {
			fiveCard_instance = new FiveCardController();
		}
		return fiveCard_instance;
	}
	
	private ArrayList<FiveCardPlayer> playerList;
	private ArrayList<Card> cards;
	private States state;
	private int buyIn;
	
	private FiveCardController() {
		playerList = new ArrayList<FiveCardPlayer>();
		cards = new ArrayList<Card>();
		
		for(Suits suit : Suits.values()) {
			for(int i = 0; i < 13; i++) {
				cards.add(new Card(i+1, suit));
			}
		}
		state = States.Empty;
	}
	
	private boolean addPlayer(UUID uuid, int amount) {
		for (FiveCardPlayer player : playerList) {
			if(player.uuid.equals(uuid)) {
				return false;
			}
		}
		
		if (GamblingController.getInstance().reduceAmount(uuid, amount)) {
			playerList.add(new FiveCardPlayer(uuid, amount));
			
			state = States.WaitingForPlayers;
			
			return true;
		}	
		return false;
	}
	

	private ArrayList<Card> NewHand() {
		
		return null;
	}

	public void getDeck(CommandSender sender) {
		sender.sendMessage("Getting deck");
		for (Card card : cards) {
			sender.sendMessage(card.toString());
		}
	}
	
	public void SuffleDeck(CommandSender sender) {
		sender.sendMessage("shuffling deck");
		Collections.shuffle(cards);		
	}
	
	public boolean placeBet(CommandSender sender, int amount) {
		if(state == States.WaitingForPlayers) {
			sender.sendMessage("gameroom already made, use /FiveCard join");
		}
		
		if(state != States.Empty) {
			sender.sendMessage("Game already in progress");
		}
				
		Player player = Bukkit.getPlayer(sender.getName());
		if(addPlayer(player.getUniqueId(), amount)) {
			buyIn = amount;
			return true;
		}
		return false;
	}
	
	public boolean placeBet(CommandSender sender) {
		Player player = Bukkit.getPlayer(sender.getName());
		if(addPlayer(player.getUniqueId(), buyIn)) {
			return true;
		}
		return false;
	}
	
	public boolean play(CommandSender sender) {
		
		return false;
	}
}
