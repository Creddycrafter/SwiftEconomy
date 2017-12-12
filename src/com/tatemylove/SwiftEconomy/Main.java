package com.tatemylove.SwiftEconomy;

import com.tatemylove.SwiftEconomy.API.SwiftEconomyAPI;
import com.tatemylove.SwiftEconomy.Commands.BalanceCommand;
import com.tatemylove.SwiftEconomy.Commands.MainCommand;
import com.tatemylove.SwiftEconomy.Commands.PayCommand;
import com.tatemylove.SwiftEconomy.Files.Playerdata;
import com.tatemylove.SwiftEconomy.Files.SignData;
import com.tatemylove.SwiftEconomy.Listeners.JoinListener;
import com.tatemylove.SwiftEconomy.Listeners.SignListener;
import com.tatemylove.SwiftEconomy.MySQL.MySQL;
import com.tatemylove.SwiftEconomy.Tasks.MoneyTask;
import com.tatemylove.SwiftEconomy.ThisPlugin.ThisPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;


public class Main extends JavaPlugin{

    public static String prefix = "§8§l[SwiftEco] ";
    public static ArrayList<Player> lockedAccount = new ArrayList<>();
    public static Economy economy = null;

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

        MoneyTask moneyTask = new MoneyTask(new SwiftEconomyAPI());
        moneyTask.runTaskTimerAsynchronously(this, 0, 20);


        ThisPlugin.getPlugin().getConfig().options().copyDefaults(true);
        ThisPlugin.getPlugin().saveDefaultConfig();
        ThisPlugin.getPlugin().reloadConfig();

        if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")) {

            String ip = ThisPlugin.getPlugin().getConfig().getString("MySQL.Ip");
            String userName = ThisPlugin.getPlugin().getConfig().getString("MySQL.Username");
            String password = ThisPlugin.getPlugin().getConfig().getString("MySQL.Password");
            String db = ThisPlugin.getPlugin().getConfig().getString("MySQL.Database");
            mySQL = new MySQL(ip, userName, password, db);
        }

        SignData.setup(this);
        Playerdata.setup(this);

        if(Bukkit.getServer().getPluginManager().getPlugin("Vault").isEnabled()) {
            setupEconomy();
            getLogger().info("Vault found! Hooking in shortly");
        }
    }

    private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(economyProvider != null){
            economy = economyProvider.getProvider();

        }
        return economy != null;
    }
}
