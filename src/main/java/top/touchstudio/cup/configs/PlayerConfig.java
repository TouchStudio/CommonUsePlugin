package top.touchstudio.cup.configs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import top.touchstudio.cup.CommonUsePlugin;

import java.io.File;
import java.io.IOException;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class PlayerConfig {
    public static File playerConfigFile;
    public static YamlConfiguration playerConfig;
    public void onServerStart(CommonUsePlugin plugin) throws IOException, InvalidConfigurationException {
        File moneyFolder = plugin.getDataFolder();
        playerConfigFile = new File(plugin.getDataFolder(),"PlayerData.yml");
        playerConfig = new YamlConfiguration();
        if (!playerConfigFile.exists()) {
            if (!moneyFolder.exists()) {
               moneyFolder.mkdirs();
            }
            playerConfigFile.createNewFile();
        }

        playerConfig.load(playerConfigFile);
    }

    public static void createPlayerData(Player player) {
        if (!playerConfig.contains(player.getName())) {
            // 创建玩家数据部分
            ConfigurationSection playerData = playerConfig.createSection(player.getName());

            // 初始化玩家的 money 数据
            playerData.set("money", 50);

            // 创建 homes 部分，确保每个玩家都可以拥有一个空的家园列表
            playerData.createSection("homes");

            // 保存配置文件
            try {
                playerConfig.save(playerConfigFile);
            } catch (IOException e) {
                e.printStackTrace(); // 这里可以打印异常信息，方便调试
            }
        }
    }



}
