package top.touchstudio.cup.modules.chainmining;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import top.touchstudio.cup.CommonUsePlugin;
import top.touchstudio.cup.utils.CU;
import top.touchstudio.cup.utils.ChatUtil;

public class ChainMiningCommand implements CommandExecutor {

    private final CommonUsePlugin plugin;

    public ChainMiningCommand(CommonUsePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(ChatColor.RED + "这个指令只能由玩家输入");
            return true;
        }

        if (args.length == 0) {
            ChatUtil.pluginSay(sender, CU.t("用法: &6/chainmining &r[&bon&r|&coff&r]"));
            return true;
        }

        if ("on".equalsIgnoreCase(args[0])) {
            plugin.setChainMiningEnabled(true);
            ChatUtil.pluginSay(sender, CU.t("&b连锁挖矿已开启"));
        } else if ("off".equalsIgnoreCase(args[0])) {
            plugin.setChainMiningEnabled(false);
            ChatUtil.pluginSay(sender, CU.t("&4连锁挖矿已关闭"));
        } else {
            ChatUtil.pluginSay(sender, CU.t("用法: &6/chainmining &r[&bon&r|&coff&r]"));
        }
        return true;
    }
}
