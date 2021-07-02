package ru.melonhell.dragoneggtweaks.tweaks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import ru.melonhell.dragoneggtweaks.Main;

public class DropEveryTime implements Listener {

    private final Main plugin;

    public DropEveryTime(Main plugin) {
        this.plugin = plugin;
    }

    private Block findPortal(World world) {
        for (int i = 40; i < 200; i++) {
            boolean skip = false;
            for (int j = 0; j < 5; j++) {
                if (!world.getBlockAt(0, i + j, 0).getType().equals(Material.BEDROCK)) {
                    skip = true;
                    continue;
                }
            }
            if (skip) continue;
            return world.getBlockAt(0, i, 0);
        }
        return null;
    }

    private boolean isPortalActive(Block portal) {
        Block block = portal.getRelative(1, 1, 1);
        return (block.getType().name().equals("END_PORTAL") || block.getType().name().equals("ENDER_PORTAL"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityDeathEvent(EntityDeathEvent event) {
        if (!Main.getMainConfig().getBoolean("DropEveryTime")) return;
        final World world = event.getEntity().getLocation().getWorld();
        if (world == null) return;
        if (!event.getEntityType().equals(EntityType.ENDER_DRAGON)) return;
        if (!world.getEnvironment().equals(World.Environment.THE_END)) return;

        double dropChance = Main.getMainConfig().getDouble("DragonEggDropChance");
        if (dropChance == 0) return;
        if (dropChance < 1 && Math.random() > dropChance) return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (int i = 0; i < 30; i++) {
                Block portal = findPortal(world);
                if (portal != null && isPortalActive(portal)) {
                    Bukkit.getScheduler().runTask(plugin, () -> portal.getRelative(0, 5, 0).setType(Material.DRAGON_EGG));
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        });
    }
}
