package me.ishy.dodgeball.GameUtil.Timers;

import me.ishy.dodgeball.GameUtil.GameManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class StartTimer extends BukkitRunnable {

    private int counter;
    public StartTimer() {
        this.counter = 5;
    }
    @Override
    public void run() {
        if(GameManager.gameState.equals(GameManager.STOPPED)){
            this.cancel();
        }
        if(counter > 0){
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eGame is beginning in " + counter + " &eseconds!"));
            counter--;
        }
        else{
            this.cancel();
        }
    }

}
