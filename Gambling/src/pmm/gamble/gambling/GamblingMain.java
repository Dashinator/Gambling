package pmm.gamble.gambling;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import pmm.gamble.gambling.Commands.CommandDouble;
import pmm.gamble.gambling.Commands.CommandFiveCard;
import pmm.gamble.gambling.Commands.CommandGamblers;

public class GamblingMain extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents((Listener) this, this);
		
		this.getCommand("Double").setExecutor(new CommandDouble());
		this.getCommand("Gamblers").setExecutor(new CommandGamblers());
		this.getCommand("FiveCard").setExecutor(new CommandFiveCard());
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
	    GamblingController.getInstance().CreateGambler(e.getPlayer());
    }
	
	
}
