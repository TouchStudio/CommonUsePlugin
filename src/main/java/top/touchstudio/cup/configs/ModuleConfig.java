package top.touchstudio.cup.configs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import top.touchstudio.cup.CommonUsePlugin;
import top.touchstudio.cup.modules.ModuleManager;

import java.io.File;
import java.io.IOException;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class ModuleConfig {
    public static File moduleConfigFile;
    public static YamlConfiguration moduleConfig;
    public static ConfigurationSection modulesSection;

    public void onServerStart(CommonUsePlugin plugin) throws IOException, InvalidConfigurationException {
        moduleConfigFile = new File(plugin.getDataFolder(), "ModuleConfig.yml");
        moduleConfig = new YamlConfiguration();
        if (!moduleConfigFile.exists()) {
            if (!plugin.getDataFolder().exists()){
                plugin.getDataFolder().mkdirs();
            }
            moduleConfigFile.createNewFile();
            moduleConfig.load(moduleConfigFile);
            moduleConfig.createSection("modules");
            modulesSection = moduleConfig.getConfigurationSection("modules");
            for (String name : ModuleManager.ModuleList) {
                modulesSection.createSection(name);
                modulesSection.set(name + ".IsEnable",true);
            }
            modulesSection.set("home.MaxHome",5);
            moduleConfig.save(moduleConfigFile);

        }
        moduleConfig.load(moduleConfigFile);
        modulesSection = moduleConfig.getConfigurationSection("modules");
        for (String name : ModuleManager.ModuleList) {
            ModuleManager.ModuleMap.put(name, modulesSection.getBoolean(name + ".IsEnable"));
            plugin.getServer().getConsoleSender().sendMessage(name + ": " + modulesSection.getBoolean(name + ".IsEnable"));

        }
        plugin.getServer().getConsoleSender().sendMessage("home.MaxHome" + ": " + modulesSection.getInt("home" + ".MaxHome"));

    }

    public void onServerDisable() throws IOException {
        ModuleManager.ModuleMap.forEach((name, isenable) -> {
            moduleConfig.set("modules." + name + ".IsEnable",isenable);
        });
            moduleConfig.save(moduleConfigFile);
    }

    public void reloadConfig(){
        CommonUsePlugin.instance.getServer().reload();
    }
}
