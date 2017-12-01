package com.tatemylove.SwiftEconomy;

import com.tatemylove.SwiftEconomy.Commands.BalanceCommand;
import com.tatemylove.SwiftEconomy.Commands.MainCommand;
import com.tatemylove.SwiftEconomy.Commands.PayCommand;
import com.tatemylove.SwiftEconomy.Files.SignData;
import com.tatemylove.SwiftEconomy.Listeners.JoinListener;
import com.tatemylove.SwiftEconomy.Listeners.SignListener;
import com.tatemylove.SwiftEconomy.MySQL.MySQL;
import com.tatemylove.SwiftEconomy.Tasks.MoneyTask;
import com.tatemylove.SwiftEconomy.ThisPlugin.ThisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{

    public static String prefix = "§8§l[SwiftEco] ";

    private MySQL mySQL;

    public void onEnable(){
        Bukkit.getServer().getPluginManager().registerEvents(new SignListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);

        MainCommand cmd = new MainCommand();
        getCommand("swift").setExecutor(cmd);

        PayCommand paycmd = new PayCommand();
        getCommand("pay").setExecutor(paycmd);

        BalanceCommand balance = new BalanceCommand();
        getCommand("balance").setExecutor(balance);

        MoneyTask moneyTask = new MoneyTask();
        moneyTask.runTaskTimerAsynchronously(this, 0, 20);


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
