package me.ishy.dodgeball.GameUtil.Timers;

import me.ishy.dodgeball.Dodgeball;
import me.ishy.dodgeball.GameUtil.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class ItemTimer extends BukkitRunnable {

    private Dodgeball plugin;


    public ItemTimer(){
        this.plugin = Dodgeball.getInstance();
    }

    @Override
    public void run() {
        if(GameManager.gameState.equals(GameManager.STOPPED)){
            this.cancel();
        }
        for(Map.Entry<Player, String> players2 : GameManager.players.entrySet()){
            if(!GameManager.out.containsKey(players2.getKey())){
                players2.getKey().getInventory().addItem(plugin.getDodgeball());
            }
        }
    }

}
