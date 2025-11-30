package top.touchstudio.cup.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

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

    @SuppressWarnings("unchecked")
    private static Map<String, Command> getKnownCommands(CommandMap commandMap) throws Exception {
        // 尝试多种可能的字段名
        String[] possibleFieldNames = {"knownCommands", "commandMap", "knownCommandMap"};

        for (String fieldName : possibleFieldNames) {
            try {
                Field knownCommandsField = commandMap.getClass().getDeclaredField(fieldName);
                knownCommandsField.setAccessible(true);
                Object fieldValue = knownCommandsField.get(commandMap);
                if (fieldValue instanceof Map) {
                    return (Map<String, Command>) fieldValue;
                }
            } catch (NoSuchFieldException e) {
                // 尝试下一个字段名
                continue;
            }
        }

        // 如果上述字段都不存在，尝试从 SimpleCommandMap 获取
        try {
            Field knownCommandsField = commandMap.getClass().getSuperclass().getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            return (Map<String, Command>) knownCommandsField.get(commandMap);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException("无法找到 knownCommands 字段");
        }
    }

    public static void unregisterCommand(Plugin plugin, String commandName) {
        // 在主线程执行命令注销操作，避免并发修改异常
        if (Bukkit.isPrimaryThread()) {
            unregisterCommandSync(plugin, commandName);
        } else {
            Bukkit.getScheduler().runTask(plugin, () -> unregisterCommandSync(plugin, commandName));
        }
    }

    private static void unregisterCommandSync(Plugin plugin, String commandName) {
        try {
            CommandMap commandMap = getCommandMap();
            Map<String, Command> knownCommands = getKnownCommands(commandMap);

            // 移除命令
            boolean removed = false;

            // 方法1: 直接移除命令名
            if (knownCommands.containsKey(commandName)) {
                Command command = knownCommands.get(commandName);
                if (command instanceof PluginCommand) {
                    PluginCommand pluginCommand = (PluginCommand) command;
                    if (pluginCommand.getPlugin().equals(plugin)) {
                        knownCommands.remove(commandName);
                        removed = true;
                    }
                } else {
                    knownCommands.remove(commandName);
                    removed = true;
                }
            }

            // 方法2: 遍历所有命令，移除匹配的命令
            Iterator<Map.Entry<String, Command>> iterator = knownCommands.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Command> entry = iterator.next();
                Command command = entry.getValue();

                if (command instanceof PluginCommand) {
                    PluginCommand pluginCommand = (PluginCommand) command;
                    if (pluginCommand.getPlugin().equals(plugin) &&
                            (pluginCommand.getName().equalsIgnoreCase(commandName) ||
                                    (pluginCommand.getAliases() != null && pluginCommand.getAliases().contains(commandName)))) {
                        iterator.remove();
                        removed = true;
                    }
                }
            }

            if (removed) {
                plugin.getLogger().info("成功移除指令: " + commandName);
            } else {
                plugin.getLogger().warning("未找到指令: " + commandName);
            }

        } catch (Exception e) {
            plugin.getLogger().warning("移除指令失败: " + commandName);
            e.printStackTrace();

            // 备用方案：使用 Bukkit 的 unregister 方法
            try {
                PluginCommand command = plugin.getServer().getPluginCommand(commandName);
                if (command != null) {
                    command.unregister(getCommandMap());
                    plugin.getLogger().info("使用备用方案成功移除指令: " + commandName);
                }
            } catch (Exception ex) {
                plugin.getLogger().warning("备用方案也失败了: " + commandName);
                ex.printStackTrace();
            }
        }
    }

    /**
     * 安全地重新注册命令（如果需要）
     */
    public static void registerCommand(Plugin plugin, String commandName, PluginCommand command) {
        if (Bukkit.isPrimaryThread()) {
            registerCommandSync(plugin, commandName, command);
        } else {
            Bukkit.getScheduler().runTask(plugin, () -> registerCommandSync(plugin, commandName, command));
        }
    }

    private static void registerCommandSync(Plugin plugin, String commandName, PluginCommand command) {
        try {
            CommandMap commandMap = getCommandMap();
            commandMap.register(plugin.getName(), command);
            plugin.getLogger().info("成功注册指令: " + commandName);
        } catch (Exception e) {
            plugin.getLogger().warning("注册指令失败: " + commandName);
            e.printStackTrace();
        }
    }

    /**
     * 更安全的命令注销方法 - 通过反射调用内部方法
     */
    public static void safeUnregisterCommand(Plugin plugin, String commandName) {
        try {
            CommandMap commandMap = getCommandMap();

            // 尝试使用 CommandMap 的 getCommand 方法获取命令
            Command command = commandMap.getCommand(commandName);
            if (command != null) {
                // 使用 Command 的 unregister 方法
                command.unregister(commandMap);
                plugin.getLogger().info("安全移除指令: " + commandName);
            } else {
                plugin.getLogger().warning("未找到指令: " + commandName);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("安全移除指令失败: " + commandName);
            e.printStackTrace();

            // 最后尝试使用原始的 unregisterCommand 方法
            unregisterCommand(plugin, commandName);
        }
    }

    /**
     * 批量移除命令
     */
    public static void unregisterCommands(Plugin plugin, String... commandNames) {
        for (String commandName : commandNames) {
            safeUnregisterCommand(plugin, commandName);
        }
    }
}