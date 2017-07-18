package me.siaco.revive;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	
	public void onEnable() {
		Logger log = getServer().getLogger();
		log.info("Enabled");
		getServer().getPluginManager().registerEvents(this, this);
		
	} 
	public void onDisable() {
		Logger log = getServer().getLogger();
		log.info("Disabled");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("zoroh") && sender instanceof Player) {
			Player p = (Player)sender;
			if(p.hasPermission("zoroh.revive")) {
				if(args.length == 0) {
					p.sendMessage(ChatColor.RED + "Usage: /zoroh revive <player>");
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("revive")) {
						Player t = Bukkit.getPlayer(args[1]);
						if(t == null) {
							p.sendMessage(ChatColor.RED + "Invalid player!");
						} else if(!(getConfig().getString("Cooldown" + "." + p.getUniqueId()) == "true")){
							getConfig().set("Cooldown" + "." + p.getUniqueId(), "true");
							saveConfig();
							p.sendMessage(ChatColor.AQUA + "Revived " + ChatColor.GREEN + t.getName() + ChatColor.AQUA + "using Zoroh Revive, you may use this again in 3hours!");
							getServer().dispatchCommand(Bukkit.getConsoleSender(), "lives revive " + t.getName());
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
								public void run() {
									getConfig().set("Cooldown" + "." + p.getUniqueId(), null);
									saveConfig();
								}
							}, 20 * 10800);
						} else {
							p.sendMessage(ChatColor.RED + "Still on cooldown!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "Usage: /zoroh revive <player>");
					}
				}
			}
		}
		return false;
	}

}
