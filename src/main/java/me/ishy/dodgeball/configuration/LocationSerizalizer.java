package me.ishy.dodgeball.configuration;

import me.ishy.dodgeball.Dodgeball;
import org.bukkit.Location;

public class LocationSerizalizer {

    private String worldname;
    private double x;
    private double y;
    private double z;
    private transient Location loc;

    public LocationSerizalizer(String worldname, double x, double y, double z){
        this.worldname = worldname;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LocationSerizalizer(Location loc){
        this.loc = loc;
        this.worldname = loc.getWorld().getName();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
    }

    public void createLoc(){
        this.loc = new Location(Dodgeball.getInstance().getServer().getWorld(worldname), x, y, z);
        if(loc == null) {
            this.loc = new Location(Dodgeball.getInstance().getServer().getWorlds().get(0), x, y, z);
        }
    }

    public Location getLoc(){
        if(this.loc == null){
            this.createLoc();
        }
        return this.loc;
    }

    @Override
    public String toString() {
        return "LocationSerizalizer{" +
                "worldname='" + worldname + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", loc=" + loc +
                '}';
    }
}
