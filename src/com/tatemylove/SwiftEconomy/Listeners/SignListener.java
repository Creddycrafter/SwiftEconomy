package com.tatemylove.SwiftEconomy.Listeners;

import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import com.tatemylove.SwiftEconomy.API.UtilsAPI;
import com.tatemylove.SwiftEconomy.Files.SignData;
import com.tatemylove.SwiftEconomy.Main;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    @EventHandler
    public void signCreate(SignChangeEvent e){
        Player p = e.getPlayer();
        Block block = e.getBlock();
        double x  = block.getX();
        double y = block.getY();
        double z = block.getZ();
        if(p.hasPermission("swifteco.signs")){
            if(e.getLine(0).equalsIgnoreCase("[Purchase]")){
                e.setLine(0, "[Purchase]");

                int sellAmount = Integer.parseInt(e.getLine(1));
                e.setLine(1, String.valueOf(sellAmount));

                String item = e.getLine(2);
                e.setLine(2, item.toUpperCase());

                int chargeAmount = Integer.parseInt(e.getLine(3));
                e.setLine(3, String.valueOf(chargeAmount));

                double total = x+y+z;
                SignData.getData().set("Buy."+  total, total);
                SignData.getData().set("Buy." + total + ".SellAmount", sellAmount);
                SignData.getData().set("Buy." + total + ".ChargeAmount", chargeAmount);
                SignData.getData().set("Buy." + total + ".Item", item);

                SignData.saveData();
                SignData.reloadData();

                p.sendMessage(Main.prefix + "Sign created at X: " +x + " Y: "+ y + " Z: " + z);
            }
        }
    }
    @EventHandler
    public void onPurchase(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getClickedBlock().getState() instanceof Sign){
            Sign sign = (Sign) e.getClickedBlock().getState();
            double x = sign.getX();
            double y = sign.getY();
            double z = sign.getZ();

            double total = x+y+z;

            if(SignData.getData().contains("Buy." + total)){
                int money = Main.money.get(p.getName());
                int chargeAmount = SignData.getData().getInt("Buy." + total + ".ChargeAmount");
                String item = SignData.getData().getString("Buy." + total + ".Item");
                int sellAmount = SignData.getData().getInt("Buy." + total + ".SellAmount");

                if(!(p.getInventory().firstEmpty() == -1)){
                    if(money >= chargeAmount){
                        p.getInventory().addItem(UtilsAPI.itemString(item, sellAmount));

                        SwiftEconomyAPI.removeMoney(p, chargeAmount);

                        p.sendMessage(Main.prefix + "Purchase successful");
                    }
                }else{
                    p.sendMessage(Main.prefix + "Purchase failed. Inventory is full!");
                }

            }
        }
    }
}
