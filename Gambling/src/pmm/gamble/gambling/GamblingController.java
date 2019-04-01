package pmm.gamble.gambling;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamblingController {
	private static GamblingController gambling_instance = null;
	
	public static GamblingController getInstance() {
		if(gambling_instance == null) {
			gambling_instance = new GamblingController();
		}
		return gambling_instance;
	}
	
	private ArrayList<Gambler> gamblers;
	
	public GamblingController(){
		gamblers = new ArrayList<Gambler>();
	}

	public void CreateGambler(Player player) {
		gamblers.add(new Gambler(player.getUniqueId()));
	}
	
	private void CreateGambler(UUID uuid, int newAmount) {
		gamblers.add(new Gambler(uuid, newAmount));
		
	}

	public void listAllGamblers(CommandSender sender) {
		String gamblersList = "";
		if(gamblers.size() == 0) {
			sender.sendMessage("No gamblers");
			return;
		}
		for (Gambler gambler : this.gamblers) {
			Player player = Bukkit.getPlayer(gambler.uuid);
			if(player == null) {
				sender.sendMessage("something went wrong");
				return;
			}
			gamblersList += player.getName();
			gamblersList += " has " + gambler.amount + " credits";
			gamblersList += "\n\n";
		}
		gamblersList = gamblersList.substring(0, gamblersList.length()-2);
		sender.sendMessage("all gamblers:\n=============\n"+gamblersList);
	}

	public boolean reduceAmount(UUID uuid, int amount) {
		for (Gambler gambler : gamblers) {
			if(gambler.uuid == uuid) {
				if(amount > gambler.amount) {
					return false;
				}
				int newAmount = gambler.amount - amount;
				removeGamblerFromList(uuid);
				CreateGambler(uuid, newAmount);
				return true;
			}
		}
		return false;
	}
	
	public boolean increaseAmount(UUID uuid, int amount) {
		for (Gambler gambler : gamblers) {
			if(gambler.uuid == uuid) {
				int newAmount = gambler.amount + amount;
				removeGamblerFromList(uuid);
				CreateGambler(uuid, newAmount);
				return true;
			}
		}
		return false;
	}

	private boolean removeGamblerFromList(UUID uuid) {
		for (int i = 0; i < gamblers.size(); i++) {
			Gambler gambler = gamblers.get(i);
			
			if(gambler.uuid.equals(uuid)) {
				gamblers.remove(i);
				return true;
			}
		}
		return false;
	}
}
