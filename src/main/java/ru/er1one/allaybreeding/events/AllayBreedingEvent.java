package ru.er1one.allaybreeding.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import ru.er1one.allaybreeding.AllayBreeding;

public class AllayBreedingEvent implements Listener {
    @EventHandler
    private void onBreeding(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(!event.getRightClicked().getType().equals(EntityType.ALLAY)) {
            return;
        }
        if(player.getInventory().getItemInMainHand() == null) {
            return;
        }
        if(!player.getInventory().getItemInMainHand().getType().equals(Material.AMETHYST_SHARD)) {
            return;
        }
        if(!AllayBreeding.getInstance().canBreedAllays(player)) {
            return;
        }
        if(!isHaveJukeboxAround(event.getRightClicked().getLocation(), AllayBreeding.getInstance().getMusicDistance())) {
            return;
        }
        player.getWorld().spawnEntity(event.getRightClicked().getLocation(), EntityType.ALLAY);
        player.getWorld().spawnParticle(Particle.HEART, event.getRightClicked().getLocation(), 3);
        AllayBreeding.getInstance().addCooldown(player);
    }

    public boolean isHaveJukeboxAround(Location start, int radius){
        for(double x = start.getX() - radius; x <= start.getX() + radius; x++){
            for(double y = start.getY() - radius; y <= start.getY() + radius; y++){
                for(double z = start.getZ() - radius; z <= start.getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    if(loc.getBlock().getState() instanceof Jukebox) {
                        Jukebox jukebox = (Jukebox) loc.getBlock().getState();
                        if(jukebox.isPlaying()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
