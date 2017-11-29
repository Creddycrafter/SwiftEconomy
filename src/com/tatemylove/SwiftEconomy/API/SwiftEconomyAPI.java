package com.tatemylove.SwiftEconomy.API;

import com.tatemylove.SwiftEconomy.Main;
import com.tatemylove.SwiftEconomy.MySQL.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SwiftEconomyAPI {

    public static void giveMoney(Player p, int amount){
        try{
            if(hasMoney(p)){
                PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE SWIFTeco SET money= money+'" + amount + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static void resetMoney(Player p){
        try{
            if(hasMoney(p)){
                PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE SWIFTeco SET money= 0'" + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void removeMoney(Player p, int amount){
        try{
            if(hasMoney(p)){
                PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE SWIFTeco SET money= money-'" + amount + "' WHERE uuid='" + p.getUniqueId().toString() + "'");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void getMoney(Player p){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SWIFTeco");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                if(rs.getString("uuid").equals(p.getUniqueId().toString())){
                    if(rs.getObject("money") != null) Main.money.put(p.getName(), rs.getInt("money"));
                }
            }
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static boolean hasMoney(Player p){
        try{
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT uuid FROM SWIFTeco");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("uuid").equals(p.getUniqueId().toString()))return true;
            }
            rs.close();
            ps.close();
            return false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static void firstMoney(Player p){
        try{
            if(!hasMoney(p)){
                int number = 0;
                PreparedStatement ps = MySQL.connection.prepareStatement("INSERT into SWIFTeco(uuid, money)\nvalues('" + p.getUniqueId().toString() + "', '" + number + "');");
                ps.executeUpdate();
                ps.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}