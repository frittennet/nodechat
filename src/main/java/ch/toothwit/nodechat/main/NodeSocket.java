package ch.toothwit.nodechat.main;

import java.net.URI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class NodeSocket {
	private static NodeSocket instance = new NodeSocket(); 
	private Socket socket; 
	public boolean connected; 
	
	public static NodeSocket get(){
		return instance; 
	} 
	
	public NodeSocket(){ 
		URI uri = URI.create("http://"+Settings.get().getServerUrl()+":"+Integer.toString(Settings.get().getServerPort())+"/"); 
		
		socket = IO.socket(uri); 
		
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() { 
			public void call(Object... arg0) {
				connected = true; 
				socket.emit("setup", Settings.get().getServerName()); 
				for(Player player : Bukkit.getOnlinePlayers()){
					NodeSocket.get().sendJoin(player.getName(), ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(player).getPrefix()), true); 
				} 
			}
		}).on("onMessage", new Emitter.Listener() { 
			public void call(Object... arg0) {
				Bukkit.broadcastMessage((String)arg0[0]); 
			}
		}).on("onPrivateMessage", new Emitter.Listener() { 
			public void call(Object... arg0) { 
				NodeChat.get().getLogger().info(""+arg0.length); 
				if(arg0.length == 2){ 
					Player p = Bukkit.getPlayer(arg0[0].toString()); 
					
					if(p != null){ 
						p.sendMessage(arg0[1].toString()); 
					} 
				} 
			} 
		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() { 
			public void call(Object... arg0) {
				connected = false; 
			}
		}); 
		
		socket.connect(); 
	} 
	
	public void sendNodeMessage(String player, String group, String message){ 
		JSONObject messageObject = new JSONObject();
		messageObject.put("player", player);
		messageObject.put("group", group);
		messageObject.put("message", message); 
		
		socket.emit("sendMessage", messageObject); 
	} 
	
	public void sendPrivateNodeMessage(String player, String otherPlayer, String group, String message){ 
		JSONObject messageObject = new JSONObject();
		messageObject.put("player", player);
		messageObject.put("receivingPlayer", otherPlayer); 
		messageObject.put("group", group);
		messageObject.put("message", message); 
		
		socket.emit("sendPrivateMessage", messageObject); 
	}
	
	public void sendJoin(String player, String group, boolean silent){
		JSONObject messageObject = new JSONObject();
		messageObject.put("player", player);
		messageObject.put("group", group); 
		messageObject.put("silent", silent); 
		
		socket.emit("playerJoin", messageObject); 
	}
	
	public void sendQuit(String player, String group){
		JSONObject messageObject = new JSONObject();
		messageObject.put("player", player);
		messageObject.put("group", group); 
		
		socket.emit("playerQuit", messageObject); 
	} 
	
	public void disconnect() {
		this.socket.close(); 
	}
}
