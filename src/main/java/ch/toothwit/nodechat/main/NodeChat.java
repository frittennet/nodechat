package ch.toothwit.nodechat.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import ch.toothwit.nodechat.events.PlayerEventListener; 


public class NodeChat extends JavaPlugin {
	private static NodeChat instance; 
	
	@Override
	public void onEnable() { 
		Bukkit.getPluginManager().registerEvents(new PlayerEventListener(), this);
		
		getLogger().info("NodeChat was enabled");
	}

	@Override
	public void onDisable() { 
		NodeSocket.get().disconnect(); 
		
		getLogger().info("Lobby was disabled");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("g") || command.getName().equalsIgnoreCase("global")) {

		}
		return false;
	}

	public static NodeChat get() {
		return instance;
	}
	
	public NodeChat() { 
		
		
		instance = this;
	}
}