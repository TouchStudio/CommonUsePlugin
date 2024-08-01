package top.touchstudio.cup.modules.money;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import top.touchstudio.cup.configs.MoneyConfig;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class MoneyEvent implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event){
        MoneyConfig.createPlayerData(event.getPlayer());
    }

}
