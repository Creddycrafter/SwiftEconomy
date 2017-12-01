package com.tatemylove.SwiftEconomy.Listeners;

import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import com.tatemylove.SwiftEconomy.API.UtilsAPI;
import com.tatemylove.SwiftEconomy.Files.SignData;
import com.tatemylove.SwiftEconomy.Main;
import com.tatemylove.SwiftEconomy.ThisPlugin.ThisPlugin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SignListener implements Listener {

    @EventHandler
    public void signCreate(SignChangeEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        if (p.hasPermission("swifteco.signs")) {
            if (e.getLine(0).equalsIgnoreCase("[Purchase]")) {
                e.setLine(0, "§2§l[Purchase]");

                int sellAmount = Integer.parseInt(e.getLine(1));
                e.setLine(1, String.valueOf(sellAmount));

                String item = e.getLine(2);
                e.setLine(2, item.toUpperCase());

                int chargeAmount = Integer.parseInt(e.getLine(3));
                e.setLine(3, ThisPlugin.getPlugin().getConfig().getString("currency") + String.valueOf(chargeAmount));

                int total = x + y + z;
                SignData.getData().set("Buy." + total + ".SellAmount", sellAmount);
                SignData.getData().set("Buy." + total + ".ChargeAmount", chargeAmount);
                SignData.getData().set("Buy." + total + ".Item", item);

                SignData.saveData();
                SignData.reloadData();

                p.sendMessage(Main.prefix + "§dSign created at X: " + x + " Y: " + y + " Z: " + z);
            } else if (e.getLine(0).equalsIgnoreCase("[Sell]")) {
                e.setLine(0, "§c§l[Sell]");

                int sellAmount = Integer.parseInt(e.getLine(1));
                e.setLine(1, String.valueOf(sellAmount));

                String item = e.getLine(2);
                e.setLine(2, item.toUpperCase());

                int chargeAmount = Integer.parseInt(e.getLine(3));
                e.setLine(3, ThisPlugin.getPlugin().getConfig().getString("currency") + String.valueOf(chargeAmount));

                int total = x + y + z;
                SignData.getData().set("Sell." + total + ".SellAmount", sellAmount);
                SignData.getData().set("Sell." + total + ".ChargeAmount", chargeAmount);
                SignData.getData().set("Sell." + total + ".Item", item);

                SignData.saveData();
                SignData.reloadData();

                p.sendMessage(Main.prefix + "§dSign created at X: " + x + " Y: " + y + " Z: " + z);

            }
        }
    }

    @EventHandler
    public void onPurchase(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock().getState() instanceof Sign) {
            if (!Main.lockedAccount.contains(p)) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                int x = sign.getX();
                int y = sign.getY();
                int z = sign.getZ();

                int total = x + y + z;
                if (sign.getLine(0).equals("§2§l[Purchase]")) {
                    if (SignData.getData().contains("Buy." + total)) {
                        int money = SwiftEconomyAPI.playerMoney.get(p.getName());
                        int chargeAmount = SignData.getData().getInt("Buy." + total + ".ChargeAmount");
                        String item = SignData.getData().getString("Buy." + total + ".Item");
                        int sellAmount = SignData.getData().getInt("Buy." + total + ".SellAmount");

                        if (!(p.getInventory().firstEmpty() == -1)) {
                            if (money >= chargeAmount) {
                                p.getInventory().addItem(UtilsAPI.itemString(item.toUpperCase(), sellAmount));

                                SwiftEconomyAPI.removeMoney(p, chargeAmount);

                                p.sendMessage(Main.prefix + "§aPurchase successful");
                            } else {
                                p.sendMessage(Main.prefix + "§cPurchase failed, insufficient funds");
                            }
                        } else {
                            p.sendMessage(Main.prefix + "§cPurchase failed. Inventory is full!");
                        }

                    }
                }
            }else{
                p.sendMessage(Main.prefix + "§c§lYour Account has been locked by an Administrator");
            }
        }
    }

    @EventHandler
    public void onSell(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock().getState() instanceof Sign) {
            if (!Main.lockedAccount.contains(p)) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                int x = sign.getX();
                int y = sign.getY();
                int z = sign.getZ();

                int total = x + y + z;

                if (sign.getLine(0).equals("§c§l[Sell]")) {
                    if (SignData.getData().contains("Sell." + total)) {
                        int chargeAmount = SignData.getData().getInt("Sell." + total + ".ChargeAmount");
                        String item = SignData.getData().getString("Sell." + total + ".Item");
                        int sellAmount = SignData.getData().getInt("Sell." + total + ".SellAmount");

                        if (p.getInventory().contains(Material.getMaterial(item.toUpperCase()), sellAmount)) {
                            p.getInventory().removeItem(new ItemStack(Material.getMaterial(item.toUpperCase()), sellAmount));
                            p.sendMessage(Main.prefix + "§a§lYou received §e§l" + ThisPlugin.getPlugin().getConfig().getString("currency") + chargeAmount);
                            SwiftEconomyAPI.giveMoney(p, chargeAmount);
                        } else {
                            p.sendMessage(Main.prefix + "§cYou don't have " + sellAmount + " " + item);
                        }
                    }
                }
            }else{
                p.sendMessage(Main.prefix + "§c§lYour Account has been locked by an Administrator");
            }
        }
    }
}
