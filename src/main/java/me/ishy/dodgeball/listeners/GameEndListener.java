package me.ishy.dodgeball.listeners;

import me.ishy.dodgeball.GameUtil.GameManager;
import me.ishy.dodgeball.GameUtil.InventoryManager;
import me.ishy.dodgeball.events.GameEndEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

public class GameEndListener implements Listener {

    @EventHandler
    public void onGameEnd(GameEndEvent e){

        if(e.getWinningTeam() == null){
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eThe game has ended with no winner."));
        }
        else{
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eThe " + e.getWinningTeam() + " &eteam has won the game!"));
        }
        for(Map.Entry<Player, String> players2 : GameManager.players.entrySet()){
            if(players2.getValue().equals(e.getWinningTeam())){
                //add rewards?
                for(String cmd : GameManager.rewards){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", players2.getKey().getName()));
                }
            }

            players2.getKey().teleport(GameManager.prevLoc.get(players2.getKey()));
            InventoryManager.returnInv(players2.getKey());
        }
        GameManager.gameState = GameManager.STOPPED;
        GameManager.reset();
    }
}
