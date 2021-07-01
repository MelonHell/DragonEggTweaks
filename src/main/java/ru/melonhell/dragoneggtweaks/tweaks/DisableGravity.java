package ru.melonhell.dragoneggtweaks.tweaks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import ru.melonhell.dragoneggtweaks.Main;

public class DisableGravity implements Listener {
    @EventHandler
    private void entityChangeBlockEvent(EntityChangeBlockEvent e) {
        if (!Main.getMainConfig().getBoolean("DisableGravity")) return;
        if (e.getEntityType() == EntityType.FALLING_BLOCK) {
            Block b = e.getBlock();
            if (b.getType() == Material.DRAGON_EGG) {
                e.setCancelled(true);
                b.getState().update(false, false);
            }
        }

    }
}
