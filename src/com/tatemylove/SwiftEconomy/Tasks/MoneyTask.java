package com.tatemylove.SwiftEconomy.Tasks;

import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Tate on 11/30/2017.
 */
public class MoneyTask extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            SwiftEconomyAPI.getMoney(p);
        }
    }
}
