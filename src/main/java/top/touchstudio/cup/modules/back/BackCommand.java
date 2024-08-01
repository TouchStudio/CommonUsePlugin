package top.touchstudio.cup.modules.back;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;
import top.touchstudio.cup.CommonUsePlugin;
import top.touchstudio.cup.utils.ChatUtil;

import java.util.HashMap;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class BackCommand implements CommandExecutor, Listener {
    public static HashMap<Player, Location> BackMap = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (!BackMap.containsKey(player)){
            ChatUtil.pluginSay(player,"未找到死亡地点");
            return false;
        }

        ChatUtil.pluginSay(player,"将在三秒后传送");
        Bukkit.getScheduler().runTaskLater(CommonUsePlugin.instance, new Runnable() {
            @Override
            public void run() {
                player.teleport(BackMap.get(player));
                BackMap.remove(player);
            }
        }, 3 * 20); // 3秒，单位为tick，20 tick = 1秒

        return false;
    }


    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getPlayer();
        BackMap.put(player,player.getLocation());
        ChatUtil.pluginSay(player,"使用/back命令返回死亡地点");
    }
}
