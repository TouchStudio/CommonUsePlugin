package top.touchstudio.cup;

import org.bukkit.plugin.java.JavaPlugin;
import top.touchstudio.cup.modules.chainmining.ChainMiningListener;
import top.touchstudio.cup.modules.chainmining.ChainMiningCommand;
import top.touchstudio.cup.modules.nightvision.NightVisionCommand;
import top.touchstudio.cup.modules.quit.QuitCommand;
import top.touchstudio.cup.modules.sneakspeedtree.SneakSpeedTreeListener;

/**
 * @Autho TouchStudio
 * @Github https://github.com/TouchStudio
 * @Date 2024-07-30 22:00
 */

public final class CommonUsePlugin extends JavaPlugin {

    private boolean chainMiningEnabled = false; // 声明变量

    @Override
    public void onEnable() {
        //跳舞树
        getServer().getPluginManager().registerEvents(new SneakSpeedTreeListener(), this);

        //quit
        getCommand("quit").setExecutor(new QuitCommand());

        //连锁挖矿
        getServer().getPluginManager().registerEvents(new ChainMiningListener(this), this); // 传递插件实例
        getCommand("chainmining").setExecutor(new ChainMiningCommand(this));
        getCommand("cm").setExecutor(new ChainMiningCommand(this));

        //夜视
        getCommand("nv").setExecutor(new NightVisionCommand());
        getCommand("nightvision").setExecutor(new NightVisionCommand());
        getCommand("夜视").setExecutor(new NightVisionCommand());
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
