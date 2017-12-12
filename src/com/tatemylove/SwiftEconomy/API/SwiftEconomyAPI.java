package com.tatemylove.SwiftEconomy.API;

import com.tatemylove.SwiftEconomy.Files.Playerdata;
import com.tatemylove.SwiftEconomy.Main;
import com.tatemylove.SwiftEconomy.MySQL.MySQL;
import com.tatemylove.SwiftEconomy.ThisPlugin.ThisPlugin;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class SwiftEconomyAPI {

    public static HashMap<String, Double> playerMoney = new HashMap<>();
    private static SwiftEconomyAPI swiftEconomyAPI = null;

    public SwiftEconomyAPI(){
        swiftEconomyAPI = SwiftEconomyAPI.this;
    }

    public void giveMoney(Player p, double amount){
        try {
            if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")) {
                if (hasMoney(p)) {
                    PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE SWIFTeco SET money= money+'" + amount + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
                    ps.executeUpdate();
                    ps.close();
                }
            }else{
                double current = Playerdata.getData().getDouble(p.getUniqueId().toString() + ".Money");
                Playerdata.getData().set(p.getUniqueId().toString() + ".Money", current+amount);
                Playerdata.saveData();
                Playerdata.reloadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetMoney(Player p) {
        try {
            if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")) {
                if (hasMoney(p)) {
                    PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE SWIFTeco SET money='" + 0 + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
                    ps.executeUpdate();
                    ps.close();
                }
            }else{
                Playerdata.getData().set(p.getUniqueId().toString() + ".Money", 0);
                Playerdata.saveData();
                Playerdata.reloadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void removeMoney(Player p, double amount) {
        try {
            if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")) {
                if (hasMoney(p)) {
                    PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE SWIFTeco SET money= money-'" + amount + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
                    ps.executeUpdate();
                    ps.close();
                }
            }else{
                double current = Playerdata.getData().getDouble(p.getUniqueId().toString() + ".Money");
                Playerdata.getData().set(p.getUniqueId().toString() + ".Money", current-amount);
                Playerdata.saveData();
                Playerdata.reloadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void getMoney(Player p) {
        try {
            if (ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")) {
                PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SWIFTeco");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getString("uuid").equals(p.getUniqueId().toString())) {
                        if (rs.getObject("money") != null) playerMoney.put(p.getName(), rs.getDouble("money"));
                    }
                }

                rs.close();
                ps.close();
            }else{
                double money = Playerdata.getData().getDouble(p.getUniqueId().toString() + ".Money");
                playerMoney.put(p.getName(), money);
            }
            } catch(Exception e){
                e.printStackTrace();
            }
        }


    public  boolean hasMoney(Player p) {
        try {
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM SWIFTeco");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("uuid").equals(p.getUniqueId().toString())) return true;
            }
            rs.close();
            ps.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public  void firstMoney(Player p) {
        try {
            if (!hasMoney(p)) {
                double number = ThisPlugin.getPlugin().getConfig().getDouble("starting-amount");
                PreparedStatement ps = MySQL.connection.prepareStatement("INSERT into SWIFTeco(uuid, money)\nvalues('" + p.getUniqueId().toString() + "', '" + number + "');");
                ps.executeUpdate();
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private  boolean isInDataBase(Player p) {
        try {
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM SWIFTlocked");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("uuid").equalsIgnoreCase(p.getUniqueId().toString())) return true;
            }
            rs.close();
            ps.close();
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public  void addUserToDB(Player p) {
        try {
            String value = "false";
            if(!isInDataBase(p)){
                PreparedStatement ps = MySQL.connection.prepareStatement("INSERT into SWIFTlocked(uuid, status)\nvalues('" + p.getUniqueId().toString() + "', '" + value + "');");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public  boolean isLocked(Player p) {
        try {

            if (isInDataBase(p)) {
                PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SWIFTlocked");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getString("uuid").equalsIgnoreCase(p.getUniqueId().toString()) && rs.getString("status").equals("true"))
                        return true;

                }

                rs.close();
                ps.close();
                return false;
            }
            }catch(Exception e){
                    e.printStackTrace();
                }
                return false;

    }
    public  void lockAccount(Player p, String value){
        try{
            if(ThisPlugin.getPlugin().getConfig().getBoolean("MySQL.Enabled")) {
                if (isInDataBase(p)) {
                    PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE SWIFTlocked SET status='" + value + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
                    ps.executeUpdate();
                    ps.close();
                }
            }else {
                if (!Playerdata.getData().getBoolean(p.getUniqueId().toString() + ".Locked")) {
                    Playerdata.getData().set(p.getUniqueId().toString() + ".Locked", true);
                    Playerdata.saveData();
                    Playerdata.reloadData();
                }else{
                    Playerdata.getData().set(p.getUniqueId().toString() + ".Locked", false);
                    Playerdata.saveData();
                    Playerdata.reloadData();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
