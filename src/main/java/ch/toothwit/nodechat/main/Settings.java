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
	}

	public void saveConfig() {
		config.set("node.url", serverUrl);  
		config.set("node.serverPort", serverPort); 
		config.set("node.name", serverName);
		
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
}
