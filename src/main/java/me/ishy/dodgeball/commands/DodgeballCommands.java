package me.ishy.dodgeball.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import me.ishy.dodgeball.Dodgeball;
import me.ishy.dodgeball.GameUtil.GameManager;
import me.ishy.dodgeball.GameUtil.InventoryManager;
import me.ishy.dodgeball.configuration.JsonConfig;
import me.ishy.dodgeball.events.GameEndEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("dodgeball|dball")
public class DodgeballCommands extends BaseCommand {

    @CommandPermission("dodgeball.admin")
    @Subcommand("start")
    public static void onStart(Player player){

        if(!GameManager.isStartable()){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eYou cannot start a game until both teams have at least one spawnpoint, and the spectate point is properly set."));
            return;
        }
        if(GameManager.gameState.equals(GameManager.STARTED)){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eYou cannot start another game until the current game ends."));
            return;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eYou have successfully started a game of &cDodgeball!"));

        GameManager.startTimer();

    }

    @CommandPermission("dodgeball.admin")
    @Subcommand("stop")
    public static void onStop(Player player){

        if(GameManager.gameState.equals(GameManager.STOPPED)){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eThere is no game in progress to stop."));
            return;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eYou have successfully stopped the current game of &cDodgeball&e!"));
        Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(null));

    }

    @CommandPermission("dodgeball.admin")
    @Subcommand("setspectate")
    public static void onSetSpectate(Player player){
        GameManager.spectate = player.getLocation();
        Dodgeball.conf.setSpectateSerialized(GameManager.spectate);
        JsonConfig.save(Dodgeball.conf);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eYou have successfully set the spectate point to " + player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ()));
    }

    @CommandPermission("dodgeball.admin")
    @CommandCompletion("team1|team2")
    @Subcommand("setspawn")
    public static void onSetSpawn(Player player, String team){
        if(team.equalsIgnoreCase("team1")) {
            GameManager.team1SpawnPoints.add(player.getLocation());
            Dodgeball.conf.setT1s(GameManager.team1SpawnPoints);
            JsonConfig.save(Dodgeball.conf);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&lDodgeball&8] &eYou have successfully set a new  spawnpoint for " + GameManager.TEAM1 + " team to " + player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ()));
        }
        else if(team.equalsIgnoreCase("team2")){
            GameManager.team2SpawnPoints.add(player.getLocation());
            Dodgeball.conf.setT2s(GameManager.team2SpawnPoints);
            JsonConfig.save(Dodgeball.conf);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&lDodgeball&8] &eYou have successfully set a new  spawnpoint for " + GameManager.TEAM2 + " team to " + player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ()));
        }
        else{
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&lDodgeball&8] &eYou must enter a valid team to set a spawn for."));
        }
    }

    @CommandPermission("dodgeball.admin")
    @Subcommand("reload")
    public static void onReload(Player player){
        Dodgeball.conf = JsonConfig.setup();
        GameManager.loadSettings(Dodgeball.conf);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&lDodgeball&8] &eYou successfully reloaded the config!"));
    }

    @CommandPermission("dodgeball.player")
    @Subcommand("join")
    public static void onJoin(Player player){

        if(GameManager.gameState.equals(GameManager.STOPPED)){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eThere is no running game of &cDodgeball&e to join."));
            return;
        }

        if(GameManager.gameState.equals(GameManager.INPROGRESS)){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eYou cannot join a &cDodgeball&e game that is currently in progress."));
            return;
        }

        if(GameManager.players.containsKey(player)){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&lDodgeball&8] &eYou must leave the &cDodgeball&e game before joining again."));
            return;
        }

        GameManager.prevLoc.put(player,player.getLocation());
        player.teleport(GameManager.spectate);
        GameManager.joinGame(player);
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &6" + player.getName() + " &ehas joined the game on " + GameManager.players.get(player) + " team"));

    }

    @CommandPermission("dodgeball.player")
    @Subcommand("leave")
    public static void onLeave(Player player){

        if(GameManager.gameState.equals(GameManager.STOPPED)){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eThere is no running game of &cDodgeball&e to leave."));
            return;
        }

        if(!GameManager.players.containsKey(player)){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eYou cannot leave a &cDodgeball&e game that you are not part of."));
            return;
        }

        InventoryManager.returnInv(player);
        if(GameManager.players.get(player) == GameManager.TEAM1){
            GameManager.team1--;
        }
        else if(GameManager.players.get(player) == GameManager.TEAM2){
            GameManager.team2--;
        }
        player.teleport(GameManager.prevLoc.get(player));
        GameManager.prevLoc.remove(player);
        GameManager.players.remove(player);
        if(GameManager.out.containsKey(player)){
            GameManager.out.remove(player);
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&c&lDodgeball&8] &eYou successfully left the current &cDodgeball&e game."));

    }
}
