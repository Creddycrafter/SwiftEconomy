package com.tatemylove.SwiftEconomy.API;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class UtilsAPI {
    public static ItemStack itemAPI(Material m, int amount){
        ItemStack i = new ItemStack(m, amount);

        return i;
    }
    public static ItemStack itemNum(int id, int amount){
        ItemStack i = new ItemStack(id, amount);

        return i;
    }
    public static ItemStack itemString(String item, int amount){
        ItemStack i = new ItemStack(Material.getMaterial(item) , amount);

        return i;
    }
}
