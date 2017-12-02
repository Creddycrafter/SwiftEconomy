package com.tatemylove.SwiftEconomy.Commands;

import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import com.tatemylove.SwiftEconomy.Main;
import com.tatemylove.SwiftEconomy.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        if(args.length == 0){
            p.sendMessage(Main.prefix + "Incorrect Argument use /pay <player> <amount>");

            return true;
        }

        if(args.length == 1){
            p.sendMessage(Main.prefix + "Incorrect Argument use /pay <player> <amount>");
            return true;
        }

        if(args.length == 2) {
            if (!Main.lockedAccount.contains(p)) {
                Player targeter = Bukkit.getPlayer(args[0]);
                double amount = Double.parseDouble(args[1]);

                double money = SwiftEconomyAPI.playerMoney.get(p.getName());

                if(p != targeter) {
                    if (money >= amount) {

                        p.sendMessage(Main.prefix + "§b§lYou paid " + targeter.getName() + " §e§l" + ThisPlugin.getPlugin().getConfig().getString("currency") + amount);

                        targeter.sendMessage(Main.prefix + "§b§l" + p.getName() + " §e§lpaid you §a§l" + ThisPlugin.getPlugin().getConfig().getString("currency") + amount);

                        SwiftEconomyAPI.giveMoney(targeter, amount);
                        SwiftEconomyAPI.removeMoney(p, amount);
                    } else {
                        p.sendMessage(Main.prefix + "§c§lInsufficient funds");
                    }
                }else{
                    p.sendMessage(Main.prefix + "§c§lYou cannot pay yourself");
                }
            }else{
                p.sendMessage(Main.prefix + "§e§lYour Account has been locked by an Administrator");
            }
        }

        return true;
    }
}
