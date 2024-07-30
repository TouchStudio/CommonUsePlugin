package top.touchstudio.cup;

import org.bukkit.plugin.java.JavaPlugin;
import top.touchstudio.cup.listeners.SneakSpeedTree;

/**
 * @Autho TouchStudio
 * @Github https://github.com/TouchStudio
 * @Date 2024-07-30 22:00
 */

public final class CommonUsePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SneakSpeedTree(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
