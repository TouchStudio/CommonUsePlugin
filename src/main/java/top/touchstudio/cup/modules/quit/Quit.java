package top.touchstudio.cup.modules.quit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Quit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && command.getName().equalsIgnoreCase("quit")) {
            Player player = (Player) sender;
            player.kickPlayer("quit");
            return true;
        }
        return true;
    }
}
