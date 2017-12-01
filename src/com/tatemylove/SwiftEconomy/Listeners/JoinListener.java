package com.tatemylove.SwiftEconomy.Listeners;

import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import com.tatemylove.SwiftEconomy.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        SwiftEconomyAPI.firstMoney(p);
        SwiftEconomyAPI.getMoney(p);
        SwiftEconomyAPI.addUserToDB(p);

        if(SwiftEconomyAPI.isLocked(p)){
            Main.lockedAccount.add(p);
        }
    }
}
