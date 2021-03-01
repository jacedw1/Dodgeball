package me.ishy.dodgeball.configuration;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class DodgeballSettings {

    private String Team_One_Name;
    private String Team_Two_Name;
    private int Game_Start_Time;
    private int Ball_Cooldown;
    private LocationSerizalizer Spectate_Spawnpoint;
    private transient Location SpectateSerialized;
    private List<LocationSerizalizer> Team_One_Spawnpoints;
    private transient List<Location> t1s;
    private List<LocationSerizalizer> Team_Two_Spawnpoints;
    private transient List<Location> t2s;;
    private List<String> rewards;


    public DodgeballSettings(String Team_One_Name, String Team_Two_Name, int Game_Start_Time, int Ball_Cooldown, LocationSerizalizer Spectate_Spawnpoint, List<LocationSerizalizer> Team_One_Spawnpoints, List<LocationSerizalizer> Team_Two_Spawnpoints, List<String> rewards) {
        this.Team_One_Name = Team_One_Name;
        this.Team_Two_Name = Team_Two_Name;
        this.Game_Start_Time = Game_Start_Time;
        this.Ball_Cooldown = Ball_Cooldown;
        this.Spectate_Spawnpoint = Spectate_Spawnpoint;
        this.Team_One_Spawnpoints = Team_One_Spawnpoints;
        this.Team_Two_Spawnpoints = Team_Two_Spawnpoints;
        this.rewards = rewards;
    }

    public void postRead(){
        this.SpectateSerialized = Spectate_Spawnpoint.getLoc();
        this.t1s = new ArrayList<>();
        this.t2s = new ArrayList<>();
        for(LocationSerizalizer locSer : Team_One_Spawnpoints){
            this.t1s.add(locSer.getLoc());
        }
        for(LocationSerizalizer locSer : Team_Two_Spawnpoints){
            this.t2s.add(locSer.getLoc());
        }
    }

    @Override
    public String toString() {
        return "DodgeballSettings{" +
                "Team_One_Name='" + Team_One_Name + '\'' +
                ", Team_Two_Name='" + Team_Two_Name + '\'' +
                ", Game_Start_Time=" + Game_Start_Time +
                ", Ball_Cooldown=" + Ball_Cooldown +
                ", Spectate_Spawnpoint=" + Spectate_Spawnpoint +
                ", Team_One_Spawnpoints=" + Team_One_Spawnpoints +
                ", Team_Two_Spawnpoints=" + Team_Two_Spawnpoints +
                '}';
    }

    public String getTeam_One_Name() {
        return Team_One_Name;
    }

    public String getTeam_Two_Name() {
        return Team_Two_Name;
    }

    public int getGame_Start_Time() {
        return Game_Start_Time;
    }

    public int getBall_Cooldown() {
        return Ball_Cooldown;
    }

    public Location getSpectateSerialized() {
        return SpectateSerialized;
    }

    public List<Location> getT1s() {
        return t1s;
    }

    public List<Location> getT2s() {
        return t2s;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public void setTeam_One_Name(String team_One_Name) {
        Team_One_Name = team_One_Name;
    }

    public void setTeam_Two_Name(String team_Two_Name) {
        Team_Two_Name = team_Two_Name;
    }

    public void setGame_Start_Time(int game_Start_Time) {
        Game_Start_Time = game_Start_Time;
    }

    public void setBall_Cooldown(int ball_Cooldown) {
        Ball_Cooldown = ball_Cooldown;
    }

    public void setSpectateSerialized(Location spectateSerialized) {
        SpectateSerialized = spectateSerialized;
        this.Spectate_Spawnpoint = new LocationSerizalizer(this.SpectateSerialized);
    }

    public void setT1s(List<Location> t1s) {
        this.t1s = t1s;
        List<LocationSerizalizer> list = new ArrayList<>();
        for(Location loc : this.t1s){
            list.add(new LocationSerizalizer(loc));
        }
        this.Team_One_Spawnpoints = list;
    }

    public void setT2s(List<Location> t2s) {
        this.t2s = t2s;
        List<LocationSerizalizer> list = new ArrayList<>();
        for(Location loc : this.t2s){
            list.add(new LocationSerizalizer(loc));
        }
        this.Team_Two_Spawnpoints = list;
    }
}
