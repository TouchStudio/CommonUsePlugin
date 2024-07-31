package top.touchstudio.cup.modules.nightvision;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import top.touchstudio.cup.utils.CU;
import top.touchstudio.cup.utils.ChatUtil;

public class NightVisionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            ChatUtil.pluginSay(sender, "&4只有玩家可以使用此命令");
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 0) {
            ChatUtil.pluginSay(sender, "&6用法: &b/nv &6或者 &b/nightvision &6或者 &b/夜视");
            return true;
        }

        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {

            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            ChatUtil.pluginSay(player, CU.t("&4夜视已关闭"));
        } else {
            PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false);
            player.addPotionEffect(nightVision);
            ChatUtil.pluginSay(player, CU.t("&b夜视已开启"));
        }

        return true;
    }
}
