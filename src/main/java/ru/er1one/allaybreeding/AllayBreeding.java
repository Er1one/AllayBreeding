package ru.er1one.allaybreeding;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.er1one.allaybreeding.events.AllayBreedingEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class AllayBreeding extends JavaPlugin {

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    private static AllayBreeding instance;

    private long cooldown;

    private int musicDistance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new AllayBreedingEvent(), this);
        saveDefaultConfig();
        cooldown = TimeUnit.MINUTES.toMillis(getConfig().getInt("breed-cooldown"));
        musicDistance = getConfig().getInt("music-distance");
    }

    public static AllayBreeding getInstance() {
        return instance;
    }

    public int getMusicDistance() {
        return musicDistance;
    }

    public long getCooldown() {
        return cooldown;
    }

    public Map<UUID, Long> getCooldowns() {
        return cooldowns;
    }

    public void addCooldown(Player player) {
        long now = System.currentTimeMillis();
        getCooldowns().put(player.getUniqueId(), now);
    }

    public boolean canBreedAllays(Player player) {
        if(!getCooldowns().containsKey(player.getUniqueId())) {
            return true;
        }
        long now = System.currentTimeMillis();
        long time = getCooldowns().get(player.getUniqueId());
        if(now > time + getCooldown()) {
            getCooldowns().remove(player.getUniqueId());
            return true;
        }
        return false;
    }


}
