package pmm.gamble.gambling.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import pmm.gamble.gambling.DoubleOrNothing.DoubleController;
import pmm.gamble.gambling.Utils.Utils;

public class CommandDouble implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 1) {
	        sender.sendMessage("Too many arguments");
	        return false;
	    } 
	    if (args.length < 1) {
	        sender.sendMessage("Enter gambling amount");
	        return false;
	    }
	    
	    if(args[0].equalsIgnoreCase("Players")) {
	    	return DoubleController.getInstance().listPlayers(sender);
	    }
	    
	    if(args[0].equalsIgnoreCase("Play")) {
	    	return DoubleController.getInstance().Play(sender);
	    }
	    
	    if(args[0].equalsIgnoreCase("info")) {
	    	return DoubleController.getInstance().PlayerInfo(sender);
	    }
	    
	    if(args[0].equalsIgnoreCase("Payout")) {
	    	return DoubleController.getInstance().Payout(sender);
	    }
	    
	    if(!Utils.isParsable(args[0])) {
	        sender.sendMessage("Enter valid amount");
	    	return false;
	    }
	    return doubleBet(sender, Integer.parseInt(args[0]));
	}
	
	private boolean doubleBet(CommandSender sender, int amount) {
		return DoubleController.getInstance().PlaceBet(sender, amount);
	}


}