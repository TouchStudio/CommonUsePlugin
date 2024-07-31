package top.touchstudio.cup.modules.login;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import top.touchstudio.cup.CommonUsePlugin;
import top.touchstudio.cup.utils.ChatUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final CommonUsePlugin plugin;
    private final Map<UUID, BukkitRunnable> playerTimers = new HashMap<>();
    private static final long TIMEOUT = 40 * 20L; // 1 minute in ticks

    public PlayerJoinListener(CommonUsePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerDataManager.loadPlayerData(player);

        if (data == null || !data.isRegistered()) {
            ChatUtil.pluginSay(player, "请输入 &6/reg &b<密码> &b<重复密码> &r注册账号!");
            PlayerDataManager.setPlayerRegistering(player, true);
            disableAllCommandsForPlayer(player);
            startTimeoutTask(player);
        } else {
            player.sendMessage("请使用 /l 或 /login 登录");
            PlayerDataManager.setPlayerRegistering(player, true);
            disableAllCommandsForPlayer(player);
            startTimeoutTask(player);
        }

        PlayerDataManager.storePlayerInventory(player);
        PlayerDataManager.setPlayerLocked(player, true);

        if (!player.isOnGround()) {
            Location safeLocation = player.getWorld().getHighestBlockAt(player.getLocation()).getLocation().add(0, 1, 0);
            player.teleport(safeLocation);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerDataManager.restorePlayerInventory(player);
        PlayerDataManager.setPlayerLocked(player, false);
        PlayerDataManager.clearPlayerLoginStatus(player);
        PlayerDataManager.clearPlayerRegistering(player);

        BukkitRunnable timer = playerTimers.remove(player.getUniqueId());
        if (timer != null) {
            timer.cancel();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (PlayerDataManager.isPlayerLocked(player)) {
            event.setCancelled(true);
            player.sendMessage("请先完成注册或登录。");
        }
    }

    private void disableAllCommandsForPlayer(Player player) {
        SimpleCommandMap commandMap = getCommandMap();
        if (commandMap != null) {
            for (Command command : commandMap.getCommands()) {
                if (command.getName().equals("reg") || command.getName().equals("register") ||
                        command.getName().equals("l") || command.getName().equals("login")) {
                    continue;
                }
                player.addAttachment(plugin, command.getName(), false);
            }
        }
    }

    private SimpleCommandMap getCommandMap() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            return (SimpleCommandMap) field.get(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void startTimeoutTask(final Player player) {
        BukkitRunnable timer = new BukkitRunnable() {
            @Override
            public void run() {
                if (PlayerDataManager.isPlayerRegistering(player)) {
                    player.kickPlayer("输入密码超时，请重新连接并尝试输入密码。");
                }
                playerTimers.remove(player.getUniqueId());
            }
        };

        playerTimers.put(player.getUniqueId(), timer);
        timer.runTaskLater(plugin, TIMEOUT);
    }
}
