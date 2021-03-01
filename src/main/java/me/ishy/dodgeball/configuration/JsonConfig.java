package me.ishy.dodgeball.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonConfig {

    private static File file;
    private static File directory;
    private static LocationSerizalizer defaultLoc;

    public static DodgeballSettings setup(){
        defaultLoc = new LocationSerizalizer("world", 0, 0, 0);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            //make directory if it doesnt exist
            directory = new File("plugins\\Dodgeball");
            if(!directory.exists()){
                directory.mkdir();
            }
            //make file if it doesnt exist, and set defaults. read file
            file = new File(directory, "config.json");
            if (!file.exists() && file.createNewFile()) {
                //create defaults
                String Team_One_Name = "Red";
                String Team_Two_Name = "Blue";
                int Game_Start_Time = 120;
                int Ball_Cooldown = 5;
                LocationSerizalizer Spectate_Spawnpoint = defaultLoc;
                List<LocationSerizalizer> Team_One_Spawnpoints = new ArrayList<>();
                Team_One_Spawnpoints.add(defaultLoc);
                List<LocationSerizalizer> Team_Two_Spawnpoints = new ArrayList<>();
                Team_Two_Spawnpoints.add(defaultLoc);
                List<String> rewards = new ArrayList<>();
                rewards.add("give %player% diamond 1");

                DodgeballSettings defaults = new DodgeballSettings(Team_One_Name, Team_Two_Name, Game_Start_Time, Ball_Cooldown, Spectate_Spawnpoint, Team_One_Spawnpoints, Team_Two_Spawnpoints, rewards);

                //convert to json
                String json_out = gson.toJson(defaults);
                //write to file
                FileWriter writer = new FileWriter(file);
                writer.write(json_out);
                writer.close();
            }
            FileReader fR = new FileReader(file);
            DodgeballSettings settings = gson.fromJson(fR,DodgeballSettings.class);
            settings.postRead();
            fR.close();
            return settings;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void save(DodgeballSettings settings){
        try{
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json_out = gson.toJson(settings);
            FileWriter writer = new FileWriter(file);
            writer.write(json_out);
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public static LocationSerizalizer getDefaultLoc() {
        return defaultLoc;
    }
}

