package ch.toothwit.nodechat.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ch.toothwit.nodechat.events.PlayerEventListener;
import ru.tehkode.permissions.bukkit.PermissionsEx; 


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
			String message = ""; 
			if(args.length >= 1){
				for(int n=0;n<args.length;n++){
					message += args[n]; 
				}
				
				Player player = (Player)sender; 
				
				NodeSocket.get().sendNodeMessage(player.getDisplayName(), ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(player).getPrefix()), message); 
			}
			else{ 
				 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Settings.get().getGlobalMessageSyntax())); 
			} 
		}
		else if(command.getName().equalsIgnoreCase("msg") || command.getName().equalsIgnoreCase("tell") || command.getName().equalsIgnoreCase("whisper")){ 
			String message = ""; 
			if(args.length > 1){
				for(int n=1;n<args.length;n++){
					message += args[n]; 
				}
				
				Player player = (Player)sender; 
				
				NodeSocket.get().sendPrivateNodeMessage(player.getDisplayName(), args[0], ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(player).getPrefix()), message); 
			}
			else{ 
				 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Settings.get().getPrivateMessageSyntax())); 
			} 
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