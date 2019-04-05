package pmm.gamble.gambling.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pmm.gamble.gambling.FiveCard.FiveCardController;

public class CommandFiveCard implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
	        sender.sendMessage("You must be a player!");
	        return false;
		}
		
		if(args[0].equalsIgnoreCase("deck")) {
			FiveCardController.getInstance().getDeck(sender);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("deck")) {
			FiveCardController.getInstance().getDeck(sender);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("shuffle")) {
			FiveCardController.getInstance().SuffleDeck(sender);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("play")) {
			FiveCardController.getInstance().play(sender);
			return true;
		}

	    return fiveCardBet(sender, Integer.parseInt(args[0]));
	}
	
	private boolean fiveCardBet(CommandSender sender, int amount) {
		return FiveCardController.getInstance().placeBet(sender, amount);
	}
}
