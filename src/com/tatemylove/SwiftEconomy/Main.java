package com.tatemylove.SwiftEconomy;

import com.tatemylove.SwiftEconomy.Commands.MainCommand;
import com.tatemylove.SwiftEconomy.Commands.PayCommand;
import com.tatemylove.SwiftEconomy.Files.SignData;
import com.tatemylove.SwiftEconomy.Listeners.SignListener;
import com.tatemylove.SwiftEconomy.MySQL.MySQL;
import com.tatemylove.SwiftEconomy.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin{

    public static String prefix = "[SwiftEco] ";
    public static HashMap<String, Integer> money = new HashMap<>();
    private MySQL mySQL;

    public void onEnable(){
        Bukkit.getServer().getPluginManager().registerEvents(new SignListener(), this);

        MainCommand cmd = new MainCommand();
        getCommand("swift").setExecutor(cmd);

        PayCommand paycmd = new PayCommand();
        getCommand("pay").setExecutor(paycmd);

        ThisPlugin.getPlugin().getConfig().options().copyDefaults(true);
        ThisPlugin.getPlugin().saveDefaultConfig();
        ThisPlugin.getPlugin().reloadConfig();

        String ip = ThisPlugin.getPlugin().getConfig().getString("MySQL.Ip");
        String userName = ThisPlugin.getPlugin().getConfig().getString("MySQL.Username");
        String password = ThisPlugin.getPlugin().getConfig().getString("MySQL.Password");
        String db = ThisPlugin.getPlugin().getConfig().getString("MySQL.Database");
        mySQL = new MySQL(ip, userName, password, db);

        SignData.setup(this);
    }
}
