package pmm.gamble.gambling.FiveCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pmm.gamble.gambling.GamblingController;
import pmm.gamble.gambling.GamblingMain;
import pmm.gamble.gambling.Utils.Card;
import pmm.gamble.gambling.Utils.Suits;

public class FiveCardController {

	private enum States {
		Empty, WaitingForPlayers, DealingCards, Paying
	}

	private static final boolean debug = false;

	private static FiveCardController fiveCard_instance = null;

	public static FiveCardController getInstance() {
		if (fiveCard_instance == null) {
			fiveCard_instance = new FiveCardController();
		}
		return fiveCard_instance;
	}

	private ArrayList<FiveCardPlayer> playerList;
	private ArrayList<Card> cards;
	private int LastCardIndex = 0;
	private States state;
	private int buyIn;
	private UUID starter;

	private FiveCardController() {
		GamblingMain.plugin.getConfig();
		
		playerList = new ArrayList<FiveCardPlayer>();
		cards = new ArrayList<Card>();

		for (Suits suit : Suits.values()) {
			for (int i = 0; i < 13; i++) {
				cards.add(new Card(i + 1, suit));
			}
		}
		state = States.Empty;
	}

	private boolean addPlayer(UUID uuid, int amount) {
		for (FiveCardPlayer player : playerList) {
			if (player.uuid.equals(uuid)) {
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

	private ArrayList<Card> NewHand(CommandSender sender) {
		ArrayList<Card> hand = new ArrayList<Card>();
		if (cards == null) {
			sender.sendMessage("Something went wrong");
			return null;
		} else {
			if (debug) {
				sender.sendMessage("cards != null");
			}
		}

		for (int i = 0; i < 5; i++) {
			Card card = cards.get(LastCardIndex);

			if (debug) {
				sender.sendMessage("give card: " + card.toString() + " on index " + LastCardIndex);
			}

			hand.add(card);
			LastCardIndex++;
		}
		Collections.sort(hand);
		return hand;
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
		if (state == States.WaitingForPlayers) {
			sender.sendMessage("gameroom already made, use /FiveCard join");
		}

		if (state != States.Empty) {
			sender.sendMessage("Game already in progress");
		}

		Player player = Bukkit.getPlayer(sender.getName());
		if (player == null) {
			sender.sendMessage("no Player");
			return false;
		}
		if (addPlayer(player.getUniqueId(), amount)) {
			buyIn = amount;
			starter = player.getUniqueId();
			sender.sendMessage("placed bet " + amount);
			return true;
		}
		return false;
	}

	public boolean placeBet(CommandSender sender) {
		Player player = Bukkit.getPlayer(sender.getName());
		if (addPlayer(player.getUniqueId(), buyIn)) {
			return true;
		}
		return false;
	}

	public boolean play(CommandSender sender) {
		if (state != States.WaitingForPlayers) {
			sender.sendMessage("Game not waiting to start");
			return false;
		}
		if (debug) {
			sender.sendMessage("Playing");
		}
		state = States.DealingCards;

		Collections.shuffle(cards);

		if (debug) {
			sender.sendMessage("shuffled deck");
		}

		Player player = Bukkit.getPlayer(sender.getName());
		if (player == null) {
			sender.sendMessage("Something went wrong getting player");
			return false;
		}

		if (starter == player.getUniqueId()) {
			if (!GivePlayersStartingHand(sender)) {
				sender.sendMessage("Something went wrong giving cards");
				return false;
			}
		} else {
			sender.sendMessage("You are not the starting player");
			return false;
		}

		if (debug) {
			sender.sendMessage("Cards: ");
			for (FiveCardPlayer fiveCardPlayer : playerList) {
				sender.sendMessage(fiveCardPlayer.toString());
			}
		}
		
		TellPlayersHand();
		return true;
	}

	private void TellPlayersHand() {
		for (FiveCardPlayer fiveCardPlayer : playerList) {
			Player player = Bukkit.getPlayer(fiveCardPlayer.uuid);
			player.sendMessage("your cards: " + fiveCardPlayer.hand.toString());
		}
		
	}

	private boolean GivePlayersStartingHand(CommandSender sender) {

		if (debug) {
			sender.sendMessage("Giving player stating hands");
		}

		for (int i = 0; i < playerList.size(); i++) {
			if (debug) {
				sender.sendMessage("working on player: " + i);
			}

			ArrayList<Card> hand = NewHand(sender);

			if (debug) {
				sender.sendMessage("made new hand");
			}

			if (hand == null) {
				return false;
			}

			playerList.get(i).hand = hand;
			if (debug) {
				sender.sendMessage("New hand for player");
			}
		}
		return true;
	}
}
