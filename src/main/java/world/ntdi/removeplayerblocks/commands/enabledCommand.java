package world.ntdi.removeplayerblocks.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import world.ntdi.removeplayerblocks.RemovePlayerBlocks;

import java.util.List;

public class enabledCommand implements CommandExecutor, TabCompleter {
    private RemovePlayerBlocks rpb;

    public enabledCommand(RemovePlayerBlocks rpb) {
        this.rpb = rpb;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(rpb.getConfig().getString("toggle-perm"))) {
            if (args.length > 0) {
                if (args[0].equals("toggle")) {
                    String status = (!rpb.isToggled() ? ChatColor.GREEN + "ON!" : ChatColor.RED + "OFF!");
                    rpb.setToggled(!rpb.isToggled());
                    sender.sendMessage(ChatColor.BLUE + "You have toggled RPB to " + status);
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "No Permission!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return List.of("toggle");
    }
}
