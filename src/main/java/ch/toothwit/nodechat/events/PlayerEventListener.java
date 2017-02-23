package ch.toothwit.nodechat.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ch.toothwit.nodechat.main.NodeSocket;
import net.alpenblock.bungeeperms.BungeePerms; 
@SuppressWarnings("deprecation")
public class PlayerEventListener implements Listener { 
	
	@EventHandler (priority = EventPriority.HIGHEST) 
	public void onPlayerChatEvent(PlayerChatEvent event){ 
		NodeSocket ns = NodeSocket.get(); 
		if(ns.connected){ 
			Player player = event.getPlayer(); 
			ns.sendNodeMessage(player.getName(), ChatColor.translateAlternateColorCodes('&', BungeePerms.getInstance().getPermissionsManager().getUser(player.getUniqueId()).getPrefix()), event.getMessage()); 
			event.setCancelled(true); 
		} 
	} 
	
	@EventHandler (priority = EventPriority.HIGHEST) 
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){ 
		NodeSocket ns = NodeSocket.get(); 
		if(ns.connected){ 
			event.setCancelled(true); 
		} 
	} 
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event){ 
		Player player = event.getPlayer(); 
		NodeSocket.get().sendJoin(player.getName(), ChatColor.translateAlternateColorCodes('&', BungeePerms.getInstance().getPermissionsManager().getUser(player.getUniqueId()).getPrefix()), false); 
		
		event.setJoinMessage(""); 
	} 
	
	@EventHandler 
	public void onPlayerQuitEvent(PlayerQuitEvent event){ 
		Player player = event.getPlayer();  
		NodeSocket.get().sendQuit(player.getName(), ChatColor.translateAlternateColorCodes('&', BungeePerms.getInstance().getPermissionsManager().getUser(player.getUniqueId()).getPrefix()));
	
		event.setQuitMessage(""); 
	} 
} 
