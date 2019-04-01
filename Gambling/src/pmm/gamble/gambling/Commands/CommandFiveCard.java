package pmm.gamble.gambling.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import pmm.gamble.gambling.FiveCard.FiveCardController;

public class CommandFiveCard implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args[0].equalsIgnoreCase("deck")) {
			FiveCardController.getInstance().getDeck(sender);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("shuffle")) {
			FiveCardController.getInstance().SuffleDeck(sender);
			return true;
		}
		
		return false;		
	}
}
