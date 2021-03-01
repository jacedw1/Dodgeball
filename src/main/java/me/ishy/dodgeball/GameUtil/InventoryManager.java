package me.ishy.dodgeball.GameUtil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private static Map<Player, Map<Integer, ItemStack>> invs = new HashMap<>();

    public static void storeInv(Player player){

        Map<Integer, ItemStack> inv = new HashMap<>();
        for(int i = 0; i < player.getInventory().getSize(); i++){
            inv.put(i,player.getInventory().getItem(i));
        }
        invs.put(player, inv);
        player.getInventory().clear();
    }

    public static void returnInv(Player player){
        if(!invs.containsKey(player)){
            return;
        }

        Map<Integer, ItemStack> inv = invs.get(player);
        player.getInventory().clear();
        for(int i = 0; i < inv.size(); i++){
            player.getInventory().setItem(i,inv.get(i));
        }
        invs.remove(player);
    }

    public static void clearInvs(){
        invs.clear();
    }
}
