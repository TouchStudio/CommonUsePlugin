package top.touchstudio.cup;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import top.touchstudio.cup.configs.ModuleConfig;
import top.touchstudio.cup.configs.MoneyConfig;
import top.touchstudio.cup.modules.ModuleManager;

import java.io.IOException;

/**
 * @Autho TouchStudio
 * @Github https://github.com/TouchStudio
 * @Date 2024-07-30 22:00
 */

public final class CommonUsePlugin extends JavaPlugin {
    public static CommonUsePlugin instance;

    @Override
    public void onEnable() {

        instance = this;
        ModuleManager moduleManager = new ModuleManager();
        moduleManager.onServerStart(this);

        MoneyConfig moneyConfig = new MoneyConfig();
        try {
            moneyConfig.onServerStart(this);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ModuleConfig moduleConfig = new ModuleConfig();
        try {
            moduleConfig.onServerDisable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}