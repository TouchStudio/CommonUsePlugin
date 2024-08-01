package top.touchstudio.cup.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.touchstudio.cup.configs.ModuleConfig;
import top.touchstudio.cup.utils.ChatUtil;

import java.util.ArrayList;
import java.util.List;

import static top.touchstudio.cup.modules.ModuleManager.ModuleList;
import static top.touchstudio.cup.modules.ModuleManager.ModuleMap;

public class CupCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.isOp()) {
            ChatUtil.pluginSay(commandSender,"&4你不是OP!");
            return false;
        }

        Player player = (Player) commandSender;

        if (strings.length >= 1 && strings[0].equalsIgnoreCase("info")) {
            player.sendMessage("-----CUP-----");
            ModuleMap.forEach((moduleName, isEnabled) -> {
                player.sendMessage("    " + moduleName + ": " + isEnabled);
            });
            return true;
        }


        if (strings.length == 2 && strings[0].equalsIgnoreCase("set")) {
            String moduleName = strings[1];
            if (!ModuleList.contains(moduleName)) {
                ChatUtil.pluginSay(player,"&4未找到此模块!");
                return true;
            }
            ChatUtil.pluginSay(player,"&4请输入正确的值");
            return true;
        }

        if (strings.length == 3 && strings[0].equalsIgnoreCase("set")) {
            String moduleName = strings[1];
            if (!ModuleList.contains(moduleName)) {
                ChatUtil.pluginSay(player,"&4未找到此模块");
                return true;
            }
            boolean value;
            switch (strings[2]) {
                case "true":
                    value = true;
                    break;
                case "false":
                    value = false;
                    break;
                default:
                    ChatUtil.pluginSay(player,"&6请输入正确的值 &r[&btrue&r | &4false&r]");
                    return true;
            }

            ModuleMap.put(moduleName, value);
            ChatUtil.pluginSay(player,"&r已将&6"  + moduleName +  "&r设置为&b"  + value + "&r");
            ModuleConfig moduleConfig = new ModuleConfig();
            moduleConfig.reloadConfig();
            return true;
        }

        ChatUtil.pluginSay(player,"&4请输入正确的命令格式");
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> tab = new ArrayList<>();
        if (strings.length == 1) {
            tab.add("set");
            tab.add("info");
        }
        if (strings.length == 2 && strings[0].equalsIgnoreCase("set")) {
            tab.addAll(ModuleList);
        }
        if (strings.length == 3 && strings[0].equalsIgnoreCase("set")) {
            tab.add("true");
            tab.add("false");
        }
        tab.removeIf(option -> !option.toLowerCase().startsWith(strings[strings.length - 1].toLowerCase()));
        return tab;
    }
}
