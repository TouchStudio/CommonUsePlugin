package top.touchstudio.cup;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import top.touchstudio.cup.configs.ModuleConfig;
import top.touchstudio.cup.modules.ModuleManager;

import java.io.IOException;

/**
 * @Autho TouchStudio
 * @Github https://github.com/TouchStudio
 * @Date 2024-07-30 22:00
 */

public final class CommonUsePlugin extends JavaPlugin {

    private boolean chainMiningEnabled = false;
    public static CommonUsePlugin instance;

    @Override
    public void onEnable() {

        instance = this;
        ModuleManager moduleManager = new ModuleManager();
        moduleManager.onServerStart(this);


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


    //连锁挖矿
    public boolean isChainMiningEnabled() {
        return chainMiningEnabled;
    }

    public void setChainMiningEnabled(boolean enabled) {
        this.chainMiningEnabled = enabled;
    }
}