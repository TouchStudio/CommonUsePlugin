package top.touchstudio.cup.modules.bestgamamode;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.touchstudio.cup.utils.ChatUtil;

public class Gm2 implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            ChatUtil.pluginSay(sender, "&4该命令只能由玩家执行!");
            return false;
        }

        Player player = (Player) sender;

        if (!player.isOp()) {
            ChatUtil.pluginSay(player, "&4你不是OP!");
            return false;
        }

        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.SPECTATOR || player.getGameMode() == GameMode.CREATIVE) {
            player.setGameMode(GameMode.ADVENTURE);
            ChatUtil.pluginSay(player, "&b您已成为冒险模式");
        } else {
            ChatUtil.pluginSay(player, "&4你已经在冒险模式!");
        }

        return true;
    }
}
