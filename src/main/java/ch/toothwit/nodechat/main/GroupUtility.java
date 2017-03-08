package ch.toothwit.nodechat.main;

import java.util.UUID;

import org.bukkit.Bukkit;

import me.lucko.luckperms.LuckPerms; 
import me.lucko.luckperms.api.MetaUtils; 

public class GroupUtility
{
    public static String retrieveGroup(UUID id) { 
        String prefix = ""; 
        
        prefix = MetaUtils.getPrefix(
            LuckPerms.getApi().getUser(id), 
            LuckPerms.getApi().getConfiguration().getServer(), 
            Bukkit.getPlayer(id).getWorld().getName(), 
            true); 
        
        return prefix; 
    }
}
