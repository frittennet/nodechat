package ch.toothwit.nodechat.main;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;


public class Settings { 
	private static Settings instance;
	private FileConfiguration config;
	private String serverUrl; 
	private int serverPort; 
	private String serverName; 
	private String globalMessageSyntax; 
	private String privateMessageSyntax; 
	
	
	public static Settings get() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	} 

	public Settings() { 
		NodeChat.get().saveDefaultConfig(); 
		this.config = NodeChat.get().getConfig(); 
		
		reloadConfig(); 
	}

	public void reloadConfig() {
		NodeChat.get().reloadConfig(); 
		config = NodeChat.get().getConfig(); 

		serverUrl = config.getString("node.url");  
		serverPort = config.getInt("node.port"); 
		serverName = config.getString("node.name"); 
		globalMessageSyntax = config.getString("messages.globalMessageSyntax"); 
		privateMessageSyntax = config.getString("messages.privateMessageSyntax"); 
	}

	public void saveConfig() {
		config.set("node.url", serverUrl);  
		config.set("node.serverPort", serverPort); 
		config.set("node.name", serverName); 
		config.set("messages.globalMessageSyntax", globalMessageSyntax); 
		config.set("messages.privateMessageSyntax", privateMessageSyntax); 
		
		File gameConfig = new File(NodeChat.get().getDataFolder() + "/" + "config.yml");
		try {
			config.save(gameConfig);
		} catch (IOException e) {
			Bukkit.getLogger().warning("Could not save config");
		}
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
		this.saveConfig(); 
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int port) {
		this.serverPort = port;
		this.saveConfig(); 
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName; 
		this.saveConfig(); 
	} 
	
	public String getGlobalMessageSyntax(){ 
		return globalMessageSyntax; 
	} 
	
	public void setGlobalMessageSyntax(String syntax){  
		this.globalMessageSyntax = syntax; 
		this.saveConfig(); 
	} 
	
	public String getPrivateMessageSyntax(){ 
		return globalMessageSyntax; 
	} 
	
	public void setPrivateMessageSyntax(String syntax){  
		this.privateMessageSyntax = syntax; 
		this.saveConfig(); 
	} 
} 
