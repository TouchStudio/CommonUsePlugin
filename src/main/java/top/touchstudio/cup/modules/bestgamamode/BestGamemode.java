package top.touchstudio.cup.modules.bestgamamode;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.touchstudio.cup.utils.CU;
import top.touchstudio.cup.utils.ChatUtil;

import static top.touchstudio.cup.modules.ModuleManager.ModuleMap;

public class BestGamemode implements CommandExecutor {
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

        if (args.length == 0) {
            GameMode gameMode = player.getGameMode();
            ChatUtil.pluginSay(player, "&b你的模式是 &6" + gameMode.name());
            return true;
        }

        if ("0".equalsIgnoreCase(args[0])) {
            player.setGameMode(GameMode.SURVIVAL);
            ChatUtil.pluginSay(player, CU.t("&r您已成为&6 生存模式"));
        }

        if ("1".equalsIgnoreCase(args[0])) {
            player.setGameMode(GameMode.CREATIVE);
            ChatUtil.pluginSay(player, CU.t("&r您已成为&6 创造模式"));
        }

        if ("2".equalsIgnoreCase(args[0])) {
            player.setGameMode(GameMode.ADVENTURE);
            ChatUtil.pluginSay(player, CU.t("&r您已成为&6 冒险模式"));
        }

        if ("3".equalsIgnoreCase(args[0])) {
            player.setGameMode(GameMode.SPECTATOR);
            ChatUtil.pluginSay(player, CU.t("&r您已成为&6 旁观者模式"));
        }
        return true;
    }
}
