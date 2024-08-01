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
public class MoneyConfig {
    public static File moneyConfigFile;
    public static YamlConfiguration moneyConfig;
    public void onServerStart(CommonUsePlugin plugin) throws IOException, InvalidConfigurationException {
        File moneyFolder = new File(plugin.getDataFolder(),"Money");
        moneyConfigFile = new File(plugin.getDataFolder(),"Money/PlayerData.yml");
        moneyConfig = new YamlConfiguration();
        if (!moneyConfigFile.exists()) {
            if (!moneyFolder.exists()) {
               moneyFolder.mkdirs();
            }
            moneyConfigFile.createNewFile();
        }

        moneyConfig.load(moneyConfigFile);
    }

    public static void createPlayerData(Player player) {
        if (!moneyConfig.contains(player.getName())){
            ConfigurationSection playerData = moneyConfig.createSection(player.getName());
            playerData.set("money",50);
            try {
                moneyConfig.save(moneyConfigFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }


}
