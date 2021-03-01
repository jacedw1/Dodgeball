package me.ishy.dodgeball.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private String winningTeam;

    public GameEndEvent(String winningTeam){
        this.winningTeam = winningTeam;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
