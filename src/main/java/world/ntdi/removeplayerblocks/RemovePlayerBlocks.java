package world.ntdi.removeplayerblocks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.removeplayerblocks.commands.enabledCommand;

import java.util.ArrayList;
import java.util.List;

public final class RemovePlayerBlocks extends JavaPlugin implements Listener {

    private boolean toggled;

    private List<Block> blocks;

    @Override
    public void onEnable() {
        // Plugin startup logic
        toggled = true;
        blocks = new ArrayList<>();

        getConfig().options().copyDefaults();
        saveConfig();

        getServer().getPluginManager().registerEvents(this, this);

        getCommand("rpb").setExecutor(new enabledCommand(this));
        getCommand("rpb").setTabCompleter(new enabledCommand(this));

        long timeout = getConfig().getLong("remove-blocks-after") * 60 * 20;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (toggled) {
                    for (Block block : blocks) {
                        block.setType(Material.AIR);
                    }
                    blocks.clear();
                }
            }
        }.runTaskTimer(this, timeout, timeout);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && getConfig().getBoolean("ignore-blocks-from-creative")) {
            return;
        }

        blocks.add(e.getBlock());
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
}
