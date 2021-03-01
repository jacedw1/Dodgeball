package me.ishy.dodgeball.listeners;

import me.ishy.dodgeball.GameUtil.GameManager;
import me.ishy.dodgeball.events.GameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;


public class DodgeballHitListener implements Listener {

    @EventHandler
    private void onDodgeballHit(ProjectileHitEvent e){
        if(
           e.getEntityType().equals(EntityType.SNOWBALL) &&
           e.getHitEntity() instanceof Player &&
           GameManager.players.containsKey(e.getHitEntity()) &&
           e.getEntity().getShooter() instanceof Player &&
           GameManager.players.containsKey(e.getEntity().getShooter()) ){

            GameManager.out.put((Player) e.getHitEntity(), GameManager.players.get(e.getHitEntity()));

            if(GameManager.out.size() == GameManager.players.size()-1){
                Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(GameManager.players.get(e.getEntity().getShooter())));
            }

        }
    }
}
