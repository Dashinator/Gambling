package pmm.gamble.gambling.DoubleOrNothing;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pmm.gamble.gambling.GamblingController;

public class DoubleController {
	private static DoubleController double_instance = null;
	
	public static DoubleController getInstance() {
		if(double_instance == null)
			double_instance = new DoubleController();
		return double_instance;
	}
	
	
	
	private ArrayList<DoublePlayer> playerList;
	
	private DoubleController() {
		playerList = new ArrayList<DoublePlayer>();
	}
	
	public boolean addPlayer(UUID uuid, int amount) {
		for (DoublePlayer doublePlayer : playerList) {
			if(doublePlayer.uuid.equals(uuid)) {
				return false;
			}
		}
		playerList.add(new DoublePlayer(uuid, amount));
		if (GamblingController.getInstance().reduceAmount(uuid, amount)) {
			return true;
		}			
		return false;
	}
	
	private DoublePlayer getPlayer(UUID uuid) {
		for (DoublePlayer doublePlayer : playerList) {
			if(doublePlayer.uuid.equals(uuid)) {
				return doublePlayer;
			}
		}
		return null;
	}

	public boolean PlayerInfo(CommandSender sender) {
		String info = "";
		Player player = Bukkit.getPlayer(sender.getName());
		if(player == null) {
			info = "No info";
		} else {
			info = PlayerInfo(player.getUniqueId());
		}
		sender.sendMessage(info);
		return true;
	}
	
	public String PlayerInfo(UUID uuid) {
		DoublePlayer player = getPlayer(uuid);
		if(player == null) {
			return "no info";
		}
		return ("You are currently at " + player.Amount);
	}

	public boolean listPlayers(CommandSender sender) {
		if(playerList.size() == 0) {
			sender.sendMessage("Currently no players");
			return true;
		}
		String players = "players: ";
		for (DoublePlayer doublePlayer : playerList) {
			players += doublePlayer.uuid + ": " + doublePlayer.Amount + " | ";
		}
		players = players.substring(0, players.length()-2);
		sender.sendMessage(players);
		return true;
	}
	
	private void removePlayerFromPlayerlist(DoublePlayer player) {
		for (int i = 0; i < playerList.size(); i++) {
			DoublePlayer doublePlayer = playerList.get(i);
			
			if(doublePlayer.uuid.equals(player.uuid)) {
				playerList.remove(i);
			}
		}
	}
	
	
	
	
	
	public boolean Play(CommandSender sender) {
		Player player = Bukkit.getPlayer(sender.getName());
		DoublePlayer doublePlayer = getPlayer(player.getUniqueId());
		if(doublePlayer == null) {
			sender.sendMessage("Bet first.");
			return false;
		}
		
		int amount = doublePlayer.Amount;
		
		double d = Math.random();
		if(d < 0.5) {
			sender.sendMessage("You won. " ); //+ PlayerInfo(player.Username)
			win(doublePlayer);
		} else {
			sender.sendMessage("You lost " + amount);
			lose(doublePlayer);
		}
		return true;
	}

	private void win(DoublePlayer player) {
		removePlayerFromPlayerlist(player);
		addPlayer(player.uuid, player.Amount*2);
	}

	private void lose(DoublePlayer player) {
		removePlayerFromPlayerlist(player);
	}

	public boolean PlaceBet(CommandSender sender, int amount) {
		Player player = Bukkit.getPlayer(sender.getName());
		if(player == null) {
			sender.sendMessage("Console can't bet.");
			return true;
		}
		if(addPlayer(player.getUniqueId(), amount)) {
			sender.sendMessage("You have bet " + amount);
		} else {
			sender.sendMessage("You have already set a bet");
		}
		return true;
	}

	public boolean Payout(CommandSender sender) {
		Player player = Bukkit.getPlayer(sender.getName());
		DoublePlayer doublePlayer = getPlayer(player.getUniqueId());
		if(doublePlayer == null) {
			sender.sendMessage("Nothing to payout");
			return true;
		}
		if(getPlayer(doublePlayer.uuid)==null) {
			sender.sendMessage("Something went wrong during payout");
			return false;	
		}

		if (GamblingController.getInstance().increaseAmount(doublePlayer.uuid, doublePlayer.Amount)) {
			sender.sendMessage("You received " + doublePlayer.Amount);
			removePlayerFromPlayerlist(doublePlayer);
			return true;
		}			
		return false;
	}
}
