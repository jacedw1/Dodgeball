package me.ishy.dodgeball;

import co.aikar.commands.PaperCommandManager;
import me.ishy.dodgeball.GameUtil.GameManager;
import me.ishy.dodgeball.GameUtil.InventoryManager;
import me.ishy.dodgeball.commands.DodgeballCommands;
import me.ishy.dodgeball.configuration.DodgeballSettings;
import me.ishy.dodgeball.configuration.JsonConfig;
import me.ishy.dodgeball.listeners.DodgeballHitListener;
import me.ishy.dodgeball.listeners.GameEndListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class Dodgeball extends JavaPlugin {

    private PaperCommandManager manager;
    private static Dodgeball instance;
    public static DodgeballSettings conf;
    public static ItemStack DODGEBALL = new ItemStack(Material.SNOWBALL);

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        manager = new PaperCommandManager(this);
        manager.registerCommand(new DodgeballCommands());
        conf = JsonConfig.setup();
        GameManager.loadSettings(conf);
        Bukkit.getServer().getPluginManager().registerEvents(new GameEndListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DodgeballHitListener(), this);
        ItemMeta dodgeballMeta = DODGEBALL.getItemMeta();
        dodgeballMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&cDodgeball"));
        DODGEBALL.setItemMeta(dodgeballMeta);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for(Map.Entry<Player, String> players2 : GameManager.players.entrySet()){
            InventoryManager.returnInv(players2.getKey());
        }

    }

    public static Dodgeball getInstance(){
        return instance;
    }

}
