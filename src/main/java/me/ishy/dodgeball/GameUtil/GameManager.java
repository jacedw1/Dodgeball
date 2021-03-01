package me.ishy.dodgeball.GameUtil;

import me.ishy.dodgeball.Dodgeball;
import me.ishy.dodgeball.GameUtil.Timers.ItemTimer;
import me.ishy.dodgeball.GameUtil.Timers.JoinTimer;
import me.ishy.dodgeball.GameUtil.Timers.StartTimer;
import me.ishy.dodgeball.configuration.DodgeballSettings;
import me.ishy.dodgeball.configuration.JsonConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {

    //game states
    public static String STOPPED = "stopped";
    public static String STARTED = "started";
    public static String INPROGRESS = "in progress";
    //current
    public static String gameState = STOPPED;

    //want to make configurable
    public static String TEAM1;
    public static String TEAM2;
    public static int GAME_START_TIME;
    private static int BALL_COOLDOWN;

    //important storage
    public static Map<Player,String> players = new HashMap<>();
    public static Map<Player,String> out = new HashMap<>();
    public static Map<Player,Location> prevLoc = new HashMap<>();
    public static List<Location> team1SpawnPoints = new ArrayList<>();
    public static List<Location> team2SpawnPoints = new ArrayList<>();
    public static Location spectate = null;

    //team counts
    public static int team1 = 0;
    public static int team2 = 0;

    //timer and tasks
    public static JoinTimer joinTimer;
    public static StartTimer startTimer;
    public static ItemTimer itemTimer;

    public static void startTimer(){
        gameState = STARTED;
        joinTimer = new JoinTimer();
        joinTimer.runTaskTimer(Dodgeball.getInstance(), 0, 20);
    }

    public static void startGame(){
        int nextTeam1 = 0;
        int nextTeam2 = 0;
        for(Map.Entry<Player, String> players2 : players.entrySet()){
            if(players2.getValue().equals(TEAM1)){
                players2.getKey().teleport(team1SpawnPoints.get(nextTeam1));
                nextTeam1 = (nextTeam1 + 1)%team1SpawnPoints.size();
            }
            else {
                players2.getKey().teleport(team2SpawnPoints.get(nextTeam2));
                nextTeam2 = (nextTeam2 + 1) % team2SpawnPoints.size();
            }
        }
        startTimer = new StartTimer();
        itemTimer = new ItemTimer();
        startTimer.runTaskTimer(Dodgeball.getInstance(), 0, 20);
        itemTimer.runTaskTimer(Dodgeball.getInstance(), 100, BALL_COOLDOWN*20);
    }

    public static void joinGame(Player player){
        if(team1 > team2){
            players.put(player, TEAM2);
            team2++;
        }
        else {
            players.put(player, TEAM1);
            team1++;
        }
        InventoryManager.storeInv(player);
    }

    public static boolean isStartable(){
        return team1SpawnPoints.size() > 0 && team2SpawnPoints.size() > 0 && spectate != null;
    }

    public static void reset(){
        players.clear();
        out.clear();
        prevLoc.clear();
        team1 = 0;
        team2 = 0;
        InventoryManager.clearInvs();
    }

    public static void loadSettings(DodgeballSettings settings){
        TEAM1 = settings.getTeam_One_Name();
        TEAM2 = settings.getTeam_Two_Name();
        GAME_START_TIME = settings.getGame_Start_Time();
        BALL_COOLDOWN = settings.getBall_Cooldown();

        Location check = JsonConfig.getDefaultLoc().getLoc();

        if(!settings.getSpectateSerialized().equals(check)) {
            spectate = settings.getSpectateSerialized();
        }
        for(Location loc : settings.getT1s()){
            if(!loc.equals(check)){
                team1SpawnPoints.add(loc);
            }
        }
        for(Location loc : settings.getT2s()){
            if(!loc.equals(check)){
                team2SpawnPoints.add(loc);
            }
        }
    }
}
