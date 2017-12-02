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
                String target = args[1];
                int amount = Integer.parseInt(args[2]);
                Player targeter = Bukkit.getPlayer(target);

                double money = SwiftEconomyAPI.playerMoney.get(p.getName());

                if (money >= amount) {
                    p.sendMessage(Main.prefix + "You payed " + targeter.getName() + " " + ThisPlugin.getPlugin().getConfig().getString("currency") + amount);
                    SwiftEconomyAPI.removeMoney(p, amount);
                    SwiftEconomyAPI.giveMoney(targeter, amount);
                    targeter.sendMessage(Main.prefix + p.getName() + " has payed you " + ThisPlugin.getPlugin().getConfig().getString("currency") + amount);
                }
            }else{
                p.sendMessage(Main.prefix + "§e§lYour Account has been locked by an Administrator");
            }
        }

        return true;
    }
}
