package top.touchstudio.cup;

import org.bukkit.plugin.java.JavaPlugin;
import top.touchstudio.cup.modules.ChainMining.ChainMining;
import top.touchstudio.cup.modules.ChainMining.ChainMiningCommand;
import top.touchstudio.cup.modules.quit.Quit;
import top.touchstudio.cup.modules.sneakspeedtree.SneakSpeedTree;

/**
 * @Autho TouchStudio
 * @Github https://github.com/TouchStudio
 * @Date 2024-07-30 22:00
 */

public final class CommonUsePlugin extends JavaPlugin {

    private boolean chainMiningEnabled = false; // 声明变量

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SneakSpeedTree(), this);
        getServer().getPluginManager().registerEvents(new ChainMining(this), this); // 传递插件实例
        getCommand("quit").setExecutor(new Quit());

        getCommand("chainmining").setExecutor(new ChainMiningCommand(this));
        getCommand("cm").setExecutor(new ChainMiningCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }


    //连锁挖矿
    public boolean isChainMiningEnabled() {
        return chainMiningEnabled;
    }

    public void setChainMiningEnabled(boolean enabled) {
        this.chainMiningEnabled = enabled;
    }
}
