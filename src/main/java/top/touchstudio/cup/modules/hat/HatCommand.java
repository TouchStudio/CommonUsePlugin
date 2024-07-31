package top.touchstudio.cup.modules.hat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.touchstudio.cup.utils.ChatUtil;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class HatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack hat = player.getInventory().getHelmet();
        player.getInventory().setItemInMainHand(hat);
        player.getInventory().setHelmet(mainHand);
        ChatUtil.pluginSay(player,"你带上了新帽子!");



        return false;
    }
}
