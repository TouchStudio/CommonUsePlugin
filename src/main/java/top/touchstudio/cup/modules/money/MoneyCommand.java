package top.touchstudio.cup.modules.money;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.touchstudio.cup.configs.PlayerConfig;
import top.touchstudio.cup.utils.CU;
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
public class MoneyCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if ((strings.length <= 1 && strings[0].equalsIgnoreCase("info"))){
            int playerMoney = PlayerConfig.playerConfig.getInt(player.getName() + ".money");
            ChatUtil.pluginSay(player,"您目前有 " + playerMoney + " 米");
            return true;
        }


        if (strings.length <= 3 && strings[0].equalsIgnoreCase("pay")) {
            if (strings.length == 2 || strings.length == 1){
                ChatUtil.pluginSay(player,"用法 /money pay PlayerName mount");
                return false;
            }
            Player payTo = Bukkit.getPlayer(strings[1]);
            if (payTo == null){
                ChatUtil.pluginSay(player,"未找到此玩家");
                return false;
            }
            if (!strings[2].matches("\\d+")){
                ChatUtil.pluginSay(player,"&4请输入数字");
                return false;
            }
            int playerMoney = PlayerConfig.playerConfig.getInt(player.getName() + ".money");
            int payToMoney = PlayerConfig.playerConfig.getInt(payTo.getName() + ".money");

            if (playerMoney < Integer.parseInt(strings[2])){
                ChatUtil.pluginSay(player,"您的余额不足");
                return false;
            }
            payToMoney += Integer.parseInt(strings[2]);
            playerMoney -= Integer.parseInt(strings[2]);

            PlayerConfig.playerConfig.set(player.getName() + ".money",playerMoney);
            PlayerConfig.playerConfig.set(payTo.getName() + ".money",payToMoney);
            try {
                PlayerConfig.playerConfig.save(PlayerConfig.playerConfigFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ChatUtil.pluginSay(player,CU.t("&r您已向玩家&6 " + payTo.getName() + " &r转了&6 " + strings[2] + " &r米"));
            ChatUtil.pluginSay(payTo,CU.t("&r玩家&6 " + player.getName() + " &r向您转了&6 " + strings[2] + " &r米"));
            return true;
        }

        if (strings.length <= 3 && strings[0].equalsIgnoreCase("set")) {
            if (!player.isOp()) {
                ChatUtil.pluginSay(commandSender,"&4你不是OP!");
                return false;
            }
            if (strings.length == 2 || strings.length == 1) {
                ChatUtil.pluginSay(player, "用法 /money set PlayerName mount");
                return false;
            }
            Player setTo = Bukkit.getPlayer(strings[1]);
            if (setTo == null) {
                ChatUtil.pluginSay(player, "未找到此玩家");
                return false;
            }
            if (!strings[2].matches("\\d+")){
                ChatUtil.pluginSay(player,"&4请输入数字");
                return false;
            }
            PlayerConfig.playerConfig.set(setTo.getName() + ".money",Integer.parseInt(strings[2]));
            try {
                PlayerConfig.playerConfig.save(PlayerConfig.playerConfigFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ChatUtil.pluginSay(player,CU.t("&r您已将玩家&6 " + setTo.getName() + " &r的米设置为&6 " + strings[2] + " &r米"));
            ChatUtil.pluginSay(setTo,CU.t("&r管理员已将你的的米设置为&6 " + strings[2] + " &r米"));
            return true;
        }

        if (strings.length <= 3 && strings[0].equalsIgnoreCase("add")) {
            if (!player.isOp()) {
                ChatUtil.pluginSay(commandSender,"&4你不是OP!");
                return false;
            }
            if (strings.length == 2 || strings.length == 1) {
                ChatUtil.pluginSay(player, "用法 /money add PlayerName mount");
                return false;
            }
            Player addTo = Bukkit.getPlayer(strings[1]);
            if (addTo == null) {
                ChatUtil.pluginSay(player, "未找到此玩家");
                return false;
            }
            if (!strings[2].matches("\\d+")){
                ChatUtil.pluginSay(player,"&4请输入数字");
                return false;
            }
            int addToMoney = PlayerConfig.playerConfig.getInt(addTo.getName() + ".money");
            addToMoney += Integer.parseInt(strings[2]);
            PlayerConfig.playerConfig.set(addTo.getName() + ".money",addToMoney);
            try {
                PlayerConfig.playerConfig.save(PlayerConfig.playerConfigFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ChatUtil.pluginSay(player,CU.t("&r您已为玩家&6 " + addTo.getName() + " &r添加了&6 " + strings[2] + " &r米"));
            ChatUtil.pluginSay(addTo,CU.t("&r管理员给你添加了你&6 " + strings[2] + " &r米 您目前有&6 " + addToMoney + " &r米"));
            return true;
        }

        ChatUtil.pluginSay(player,"&4请输入正确的命令格式");
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> tab = new ArrayList<>();
        if (strings.length == 1) {
            tab.add("pay");
            tab.add("info");
            if (commandSender.isOp()){
                tab.add("set");
                tab.add("add");
            }
        }
        if (strings.length == 2 && strings[0].equalsIgnoreCase("pay") || strings.length == 2 && strings[0].equalsIgnoreCase("set") || strings.length == 2 && strings[0].equalsIgnoreCase("add")){
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                tab.add(onlinePlayer.getName());
            }
        }
        if (strings.length == 3 && strings[0].equalsIgnoreCase("pay") || strings.length == 3 && strings[0].equalsIgnoreCase("set") || strings.length == 3 && strings[0].equalsIgnoreCase("add")){
            tab.add("mount");
        }
        return tab;
    }
}
