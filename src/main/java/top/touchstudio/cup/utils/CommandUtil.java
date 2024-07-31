package top.touchstudio.cup.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class CommandUtil {
    private static CommandMap getCommandMap() throws NoSuchFieldException, IllegalAccessException {
        Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMapField.setAccessible(true);
        return (CommandMap) commandMapField.get(Bukkit.getServer());
    }

    public static void unregisterCommand(Plugin plugin, String commandName) {
        PluginCommand command = Bukkit.getServer().getPluginCommand(commandName);

        if (command != null) {
            try {
                CommandMap commandMap = getCommandMap();
                command.unregister(commandMap);
                plugin.getLogger().info("成功移除指令: " + commandName);
            } catch (Exception e) {
                plugin.getLogger().warning("移除指令失败: " + commandName);
                e.printStackTrace();
            }
        } else {
            plugin.getLogger().warning("未找到指令: " + commandName);
        }
    }
}
