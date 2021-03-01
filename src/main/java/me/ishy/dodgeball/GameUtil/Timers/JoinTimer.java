package me.ishy.dodgeball.GameUtil.Timers;

import me.ishy.dodgeball.GameUtil.GameManager;
import me.ishy.dodgeball.events.GameEndEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinTimer extends BukkitRunnable {

    private int counter;

    public JoinTimer(){
        this.counter = GameManager.GAME_START_TIME;
    }

    @Override
    public void run() {
        if(GameManager.gameState.equals(GameManager.STOPPED)){
            this.cancel();
            return;
        }
        if(counter > 0){
            if(counter <= 3 || counter == 10 || counter == GameManager.GAME_START_TIME || counter == GameManager.GAME_START_TIME/2 || counter == GameManager.GAME_START_TIME/4){
                Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eGame is beginning in " + counter + " &eseconds!"));
            }
            counter--;
        }
        else{
            if(GameManager.players.size() > 1) {
                GameManager.startGame();
                this.cancel();
                return;
            }
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eThe game has been cancelled due to lack of players."));
            Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(null));
            this.cancel();
        }
    }

}
