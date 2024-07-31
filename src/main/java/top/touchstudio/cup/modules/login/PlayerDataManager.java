package top.touchstudio.cup.modules.login;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {


    static {
        if (!DATA_FOLDER.exists()) {
            DATA_FOLDER.mkdirs();
        }
    }

    private static final Map<Player, Boolean> playerLocked = new HashMap<>();
    private static final Map<UUID, ItemStack[]> storedInventories = new HashMap<>();
    private static final Map<UUID, ItemStack[]> storedArmors = new HashMap<>();
    private static final Map<UUID, Boolean> playerLoginStatus = new HashMap<>();
    private static final Map<UUID, Boolean> playerRegistering = new HashMap<>();

    public static void savePlayerData(Player player, String password) {
        File playerFile = new File(DATA_FOLDER, player.getUniqueId() + ".dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(playerFile))) {
            oos.writeObject(new PlayerData(password, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerData loadPlayerData(Player player) {
        File playerFile = new File(DATA_FOLDER, player.getUniqueId() + ".dat");
        if (!playerFile.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(playerFile))) {
            return (PlayerData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setPlayerLocked(Player player, boolean locked) {
        playerLocked.put(player, locked);
        if (locked) {
            player.setWalkSpeed(0); // 禁止玩家移动
        } else {
            player.setWalkSpeed(0.2f); // 恢复玩家移动速度
        }
    }

    public static boolean isPlayerLocked(Player player) {
        return playerLocked.getOrDefault(player, false);
    }

    public static void storePlayerInventory(Player player) {
        storedInventories.put(player.getUniqueId(), player.getInventory().getContents());
        storedArmors.put(player.getUniqueId(), player.getInventory().getArmorContents());
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);
    }

    public static void restorePlayerInventory(Player player) {
        if (storedInventories.containsKey(player.getUniqueId())) {
            player.getInventory().setContents(storedInventories.remove(player.getUniqueId()));
            player.getInventory().setArmorContents(storedArmors.remove(player.getUniqueId()));
        }
    }

    public static void setPlayerLoginStatus(Player player, boolean status) {
        playerLoginStatus.put(player.getUniqueId(), status);
    }

    public static boolean isPlayerLoggedIn(Player player) {
        return playerLoginStatus.getOrDefault(player.getUniqueId(), false);
    }

    public static void clearPlayerLoginStatus(Player player) {
        playerLoginStatus.remove(player.getUniqueId());
    }

    public static void setPlayerRegistering(Player player, boolean registering) {
        playerRegistering.put(player.getUniqueId(), registering);
    }

    public static boolean isPlayerRegistering(Player player) {
        return playerRegistering.getOrDefault(player.getUniqueId(), false);
    }

    public static void clearPlayerRegistering(Player player) {
        playerRegistering.remove(player.getUniqueId());
    }
}
