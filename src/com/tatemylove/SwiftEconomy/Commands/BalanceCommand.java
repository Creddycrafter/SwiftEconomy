package com.tatemylove.SwiftEconomy.Commands;

import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import com.tatemylove.SwiftEconomy.Main;
import com.tatemylove.SwiftEconomy.ThisPlugin.ThisPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Tate on 11/30/2017.
 */
public class BalanceCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        if(args.length == 0){
            if(p.hasPermission("swifteco.balance")){
                SwiftEconomyAPI.getMoney(p);
                int money = SwiftEconomyAPI.playerMoney.get(p.getName());
                p.sendMessage(Main.prefix + "§a§lYou have §e§l" + ThisPlugin.getPlugin().getConfig().getString("currency") + money);
            }
        }
        return true;
    }
}
