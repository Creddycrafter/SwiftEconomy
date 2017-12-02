package com.tatemylove.SwiftEconomy.Listeners;

import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import com.tatemylove.SwiftEconomy.Files.Playerdata;
import com.tatemylove.SwiftEconomy.Main;
import com.tatemylove.SwiftEconomy.MySQL.MySQL;
import com.tatemylove.SwiftEconomy.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")) {
            SwiftEconomyAPI.firstMoney(p);
            SwiftEconomyAPI.getMoney(p);
            SwiftEconomyAPI.addUserToDB(p);

            if (SwiftEconomyAPI.isLocked(p)) {
                Main.lockedAccount.add(p);
            }
        }else {
            if (!Playerdata.getData().contains(p.getUniqueId().toString())) {
                Playerdata.getData().set(p.getUniqueId().toString() + ".Money", ThisPlugin.getPlugin().getConfig().getDouble("starting-amount"));
                Playerdata.getData().set(p.getUniqueId().toString() + ".Locked", false);
                Playerdata.saveData();
                Playerdata.reloadData();
            }
            if(Playerdata.getData().getBoolean(p.getUniqueId().toString() + ".Locked")){
                Main.lockedAccount.add(p);
            }
        }
    }
}
