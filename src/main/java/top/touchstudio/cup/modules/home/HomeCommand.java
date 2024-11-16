package top.touchstudio.cup.modules.home;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.touchstudio.cup.configs.ModuleConfig;
import top.touchstudio.cup.configs.PlayerConfig;
import top.touchstudio.cup.utils.ChatUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class HomeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        int maxHome = Integer.parseInt(ModuleConfig.modulesSection.get("home.MaxHome").toString());
        Player player = (Player) commandSender;
        YamlConfiguration config = PlayerConfig.playerConfig;
        ConfigurationSection homeSection = config.getConfigurationSection(player.getName()).getConfigurationSection("homes");
        if (strings.length == 1 && strings[0].equalsIgnoreCase("list")){
            if (homeSection.getKeys(false).isEmpty()){
                ChatUtil.pluginSay(player,"未找到任何Home");
                return true;
            }
            homeSection.getKeys(false).forEach(key -> {
               ChatUtil.pluginSay(player,key);
            });
            return true;

        }

        if(strings.length < 1) {
            ChatUtil.pluginSay(player,"用法 /home 名称");
            return true;
        }
        if (strings[0].equalsIgnoreCase("set")) {
            if (strings.length != 2) {
                ChatUtil.pluginSay(player,"用法 /home set 名称");
                return true;
            }

            if (homeSection.getKeys(false).size() >= maxHome){
                ChatUtil.pluginSay(player,"已达到最大Home数");
                return true;
            }
            if (homeSection != null) {
                if (homeSection.contains(strings[1])) {
                    ChatUtil.pluginSay(player,"存在相同的名字");
                    return true;
                }
            }

            homeSection.set(strings[1], player.getLocation());
            ChatUtil.pluginSay(player,"已添加 " + strings[1]);
            try {
                config.save(PlayerConfig.playerConfigFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        if (strings[0].equalsIgnoreCase("delete")) {
            if (strings.length != 2) {
                ChatUtil.pluginSay(player,"用法 /home delete 名称");
                return true;
            }
            if (homeSection != null) {
                if (!homeSection.contains(strings[1])) {
                    ChatUtil.pluginSay(player,"该Home不存在");
                    return true;
                }
            }

            homeSection.set(strings[1], null);
            ChatUtil.pluginSay(player,"已删除 " + strings[1]);
            try {
                config.save(PlayerConfig.playerConfigFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        if(strings.length  ==1) {
            if (homeSection == null || !homeSection.contains(strings[0])) {
                ChatUtil.pluginSay(player,"该Home不存在");
                return true;
            }

            Location homeLoc = (Location) homeSection.get(strings[0]);
            player.teleport(homeLoc);
            ChatUtil.pluginSay(player,"已传送到 " + strings[0]);
            return true;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1) {
            list.add("set");
            list.add("delete");
            list.add("list");
        };
        return list;
    }
}
