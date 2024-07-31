package top.touchstudio.cup.modules.login;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.entity.Player;
import top.touchstudio.cup.CommonUsePlugin;

public class CommandInterceptor implements Listener {

    private final CommonUsePlugin plugin;

    public CommandInterceptor(CommonUsePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        // 检测玩家登录状态
        if (PlayerDataManager.isPlayerRegistering(player)) {
            String command = event.getMessage().split(" ")[0].substring(1).toLowerCase();

            if (command.equals("reg") || command.equals("register") ||
                    command.equals("l") || command.equals("login")) {
                return;
            } else {
                // 禁用其他指令
                player.sendMessage("请先完成注册或登录。");
                event.setCancelled(true);
            }
        }
    }
}
