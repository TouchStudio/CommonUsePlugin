package top.touchstudio.cup.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @Author XuFang
 * @Github https://github.com/XuFangGG
 * @Date 2024-02-06 18:06:22
 */

public class ChatUtil {

    public static void say(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    public static void debug(CommandSender sender, String debugsay) {
        sender.sendMessage(ChatColor.RED + "Debug: " + debugsay);
    }

    public static void info(CommandSender sender, String infosay) {
        sender.sendMessage(ChatColor.GREEN + "Info: " + infosay);
    }

    public static void pluginSay(CommandSender sender, String pluginsay) {
        sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "CUP" + ChatColor.WHITE + "] " + ChatColor.RESET + pluginsay);
    }
}
