package ru.melonhell.dragoneggtweaks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.melonhell.dragoneggtweaks.tweaks.DisableGravity;
import ru.melonhell.dragoneggtweaks.tweaks.DisableTeleport;
import ru.melonhell.dragoneggtweaks.tweaks.DropEveryTime;

public final class Main extends JavaPlugin {

    FileConfiguration config = getConfig();

    public static FileConfiguration getMainConfig() {
        return getPlugin(Main.class).config;
    }

    @Override
    public void onEnable() {
        config.addDefault("DisableGravity", true);
        config.addDefault("DisableTeleport", true);
        config.addDefault("DropEveryTime", true);
        config.options().copyDefaults(true);
        saveConfig();

        this.getServer().getPluginManager().registerEvents(new DisableGravity(), this);
        this.getServer().getPluginManager().registerEvents(new DisableTeleport(), this);
        this.getServer().getPluginManager().registerEvents(new DropEveryTime(this), this);
    }

    @Override
    public void onDisable() {
    }
}
