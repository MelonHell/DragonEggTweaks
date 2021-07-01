package ru.melonhell.dragoneggtweaks.tweaks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.melonhell.dragoneggtweaks.Main;

public class DropEveryTime implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent e) {
        if (!Main.getMainConfig().getBoolean("DropEveryTime")) return;
        final World world = e.getEntity().getLocation().getWorld();

        if (!e.getEntityType().equals(EntityType.ENDER_DRAGON))
            return;

        if (world == null || !world.getEnvironment().equals(World.Environment.THE_END))
            return;

        new BukkitRunnable() {
            public void run() {
                Location loc = new Location(world, 0, 0, 0);
                loc.setY(world.getHighestBlockYAt(0, 0));
                Block highestBlock = world.getBlockAt(loc);

                loc.setY(world.getHighestBlockYAt(0, 0) - 1);
                Block secondHighestBlock = world.getBlockAt(loc);

                if (highestBlock.getType().equals(Material.BEDROCK)) {
                    loc.setY(world.getHighestBlockYAt(0, 0) + 1);
                    Block topBlock = world.getBlockAt(loc);
                    topBlock.setType(Material.DRAGON_EGG);
                } else if (secondHighestBlock.getType().equals(Material.BEDROCK)) {
                    highestBlock.setType(Material.DRAGON_EGG);
                }
            }
        }.runTaskLater(Main.getPlugin(Main.class), 20L * 10);

    }
}
