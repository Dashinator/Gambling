package pmm.gamble.gambling.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import pmm.gamble.gambling.GamblingController;

public class CommandGamblers implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("making a list of all gamblers");
		GamblingController controller = GamblingController.getInstance();
		sender.sendMessage("accessing controller");
		controller.listAllGamblers(sender);
		return true;
	}

}
