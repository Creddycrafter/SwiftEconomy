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
            p.sendMessage("-------=Swift Economy=-------");
            p.sendMessage("Author: tatemylove");
            p.sendMessage("Version: 1.0");
            return true;
        }
        if(args[0].equalsIgnoreCase("reset")){
            if(p.hasPermission("swifteco.reset")){
                Player target = Bukkit.getPlayer(args[1]);

                p.sendMessage(Main.prefix + "You reset " + target.getName() + " account balance");
                SwiftEconomyAPI.resetMoney(target);
            }
        }
        if(args[0].equalsIgnoreCase("remove")){
            if(p.hasPermission("swifteco.remove")){
                Player target = Bukkit.getPlayer(args[1]);
                int amount = Integer.parseInt(args[2]);

                p.sendMessage(Main.prefix + "You removed " + ThisPlugin.getPlugin().getConfig().getString("currency") +amount + "from " + target.getName());
                SwiftEconomyAPI.removeMoney(target, amount);
            }
        }
        if(args[0].equalsIgnoreCase("add")){
            if(p.hasPermission("swifteco.add")){
                Player target = Bukkit.getPlayer(args[1]);
                int amount = Integer.parseInt(args[2]);

                p.sendMessage(Main.prefix + "You gave " + target.getName() + " " + ThisPlugin.getPlugin().getConfig().getString("currency") + amount);

                SwiftEconomyAPI.giveMoney(target, amount);
            }
        }
        return true;
    }
}
