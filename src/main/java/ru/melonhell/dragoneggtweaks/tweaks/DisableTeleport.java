package ru.melonhell.dragoneggtweaks.tweaks;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import ru.melonhell.dragoneggtweaks.Main;

public class DisableTeleport implements Listener {
    @EventHandler
    public void blockFromToEvent(BlockFromToEvent event) {
        if (!Main.getMainConfig().getBoolean("DisableTeleport")) return;
        if (event.getBlock().getType().equals(Material.DRAGON_EGG)) {
            event.setCancelled(true);
        }
    }

}
