package com.tatemylove.SwiftEconomy.Commands;

import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import com.tatemylove.SwiftEconomy.Main;
import com.tatemylove.SwiftEconomy.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player p = (Player) sender;
        if(args.length == 0){
            p.sendMessage("§8§l-------=§7§lSwift Economy§8§l=-------");
            p.sendMessage("§b§lAuthor: §e§ltatemylove");
            p.sendMessage("§d§lVersion: §2§lv.1.0.3");
            return true;
        }
        if(args[0].equalsIgnoreCase("reset")){
            if(p.hasPermission("swifteco.reset")){
                Player target = Bukkit.getPlayer(args[1]);

                p.sendMessage(Main.prefix + "§b§lYou reset " + target.getName() + "'s §e§laccount balance");

                target.sendMessage(Main.prefix + "§b§l" + p.getName() + " §c§lhas reset your account balance");
                SwiftEconomyAPI.resetMoney(target);
            }
        }
        if(args[0].equalsIgnoreCase("remove")){
            if(p.hasPermission("swifteco.remove")){
                Player target = Bukkit.getPlayer(args[1]);
                double amount = Double.parseDouble(args[2]);

                p.sendMessage(Main.prefix + "§b§lYou removed " + ThisPlugin.getPlugin().getConfig().getString("currency") + "§3§l" +amount + "from " + target.getName());

                target.sendMessage(Main.prefix + "§b§l" + p.getName() + " §e§lremoved §a§l" + ThisPlugin.getPlugin().getConfig().getString("currency")+amount + " from your balance");

                SwiftEconomyAPI.removeMoney(target, amount);
            }
        }
        if(args[0].equalsIgnoreCase("add")){
            if(p.hasPermission("swifteco.add")){
                Player target = Bukkit.getPlayer(args[1]);
                double amount = Double.parseDouble(args[2]);

                p.sendMessage(Main.prefix + "§b§lYou gave " + target.getName() + " §e§l" + ThisPlugin.getPlugin().getConfig().getString("currency") + amount);

                target.sendMessage(Main.prefix + "§b§l" + p.getName() + " §e§lgave you §a§l" + ThisPlugin.getPlugin().getConfig().getString("currency")+amount);

                SwiftEconomyAPI.giveMoney(target, amount);
            }
        }
        if(args[0].equalsIgnoreCase("lock")){
            if(p.hasPermission("swifteco.lock")) {
                if (!Main.lockedAccount.contains(p)) {
                    Player target = Bukkit.getPlayer(args[1]);

                    p.sendMessage(Main.prefix + "§b§lYou locked " + target.getName() + "'s §e§laccount");

                    SwiftEconomyAPI.lockAccount(target, "true");

                    Main.lockedAccount.add(p);
                }else{
                    p.sendMessage(Main.prefix + "§b§lThis player's account is already locked");
                }
            }
        }
        if(args[0].equalsIgnoreCase("unlock")){
            if(p.hasPermission("swifteco.unlock")){
                if(Main.lockedAccount.contains(p)){
                    Player target = Bukkit.getPlayer(args[1]);

                    p.sendMessage(Main.prefix + "§b§lYou unlocked " + target.getName() + "'s §e§laccount");

                    SwiftEconomyAPI.lockAccount(p, "false");
                    Main.lockedAccount.remove(p);
                }else{
                    p.sendMessage(Main.prefix + "§c§lPlayer's account is not locked");
                }
            }
        }
        return true;
    }
}
