package com.tatemylove.SwiftEconomy.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

    @EventHandler
    public void signCreate(SignChangeEvent e){
        Player p = e.getPlayer();

        if(p.hasPermission("swifteco.buy")){
            if(e.getLine(0).equalsIgnoreCase("[Purchase]"){
                e.setLine(0, "[Purchase]");

                int sellAmount = Integer.parseInt(e.getLine(1));
                e.setLine(1, String.valueOf(sellAmount));

                String item = e.getLine(2);
                e.setLine(2, item.toUpperCase());

                int chargeAmount = Integer.parseInt(e.getLine(3));
                e.setLine(3, String.valueOf(chargeAmount));
            }
        }
    }
}
