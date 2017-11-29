package com.tatemylove.SwiftEconomy.Commands;

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
        return true;
    }
}
