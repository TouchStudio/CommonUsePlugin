package top.touchstudio.cup.modules;

import org.bukkit.configuration.InvalidConfigurationException;
import top.touchstudio.cup.command.CupCommand;
import top.touchstudio.cup.CommonUsePlugin;
import top.touchstudio.cup.configs.ModuleConfig;
import top.touchstudio.cup.utils.CommandUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Autho BaicaijunOvO
 * @Github https://github.com/BaicaijunOvO
 * @Date 2024-06 下午9:28
 * @Tips XuFang is Gay!
 */
public class ModuleManager {
    public static HashMap<String,Boolean> ModuleMap = new HashMap<>();
    public static List<String> ModuleList = new ArrayList<>();


    public void onServerStart(CommonUsePlugin plugin){

        ModuleList.add("cup");
        ModuleList.add("sneakspeedtree");
        ModuleList.add("quit");
        ModuleList.add("chainmining");
        ModuleList.add("nightvision");
        ModuleList.add("login");

        ModuleConfig moduleConfig = new ModuleConfig();
        try {
            moduleConfig.onServerStart(CommonUsePlugin.instance);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }


        if (ModuleMap.get("cup")){
            plugin.getCommand("cup").setExecutor(new CupCommand());
        }else {
            CommandUtil.unregisterCommand(plugin,"cup");
        }

        if (ModuleMap.get("sneakspeedtree")){
            plugin.getServer().getPluginManager().registerEvents(new top.touchstudio.cup.modules.sneakspeedtree.SneakSpeedTreeListener(), plugin);
        }
        //quit
        if (ModuleMap.get("quit")){
            plugin.getCommand("quit").setExecutor(new top.touchstudio.cup.modules.quit.QuitCommand());
        }else {
            CommandUtil.unregisterCommand(plugin,"quit");
        }


        //连锁挖矿
        if (ModuleMap.get("chainmining")){
            plugin.getServer().getPluginManager().registerEvents(new top.touchstudio.cup.modules.chainmining.ChainMiningListener(plugin), plugin); // 传递插件实例
            plugin.getCommand("chainmining").setExecutor(new top.touchstudio.cup.modules.chainmining.ChainMiningCommand(plugin));
            plugin.getCommand("cm").setExecutor(new top.touchstudio.cup.modules.chainmining.ChainMiningCommand(plugin));
        }else {
            CommandUtil.unregisterCommand(plugin,"chainmining");
            CommandUtil.unregisterCommand(plugin,"cm");
        }


        //夜视
        if (ModuleMap.get("nightvision")){
            plugin.getCommand("nv").setExecutor(new top.touchstudio.cup.modules.nightvision.NightVisionCommand());
            plugin.getCommand("nightvision").setExecutor(new top.touchstudio.cup.modules.nightvision.NightVisionCommand());
            plugin.getCommand("夜视").setExecutor(new top.touchstudio.cup.modules.nightvision.NightVisionCommand());
        }else {
            CommandUtil.unregisterCommand(plugin,"nv");
            CommandUtil.unregisterCommand(plugin,"nightvision");
            CommandUtil.unregisterCommand(plugin,"夜视");
        }


        //登录插件
        if (ModuleMap.get("login")){
            top.touchstudio.cup.modules.login.LoginCommand loginCommand = new top.touchstudio.cup.modules.login.LoginCommand(plugin);
            plugin.getCommand("reg").setExecutor(loginCommand);
            plugin.getCommand("register").setExecutor(loginCommand);
            plugin.getCommand("l").setExecutor(loginCommand);
            plugin.getCommand("login").setExecutor(loginCommand);

            plugin.getServer().getPluginManager().registerEvents(new top.touchstudio.cup.modules.login.PlayerJoinListener(plugin), plugin);
            plugin.getServer().getPluginManager().registerEvents(new top.touchstudio.cup.modules.login.PlayerActionListener(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new top.touchstudio.cup.modules.login.CommandInterceptor(plugin), plugin);
        }else {
            CommandUtil.unregisterCommand(plugin,"reg");
            CommandUtil.unregisterCommand(plugin,"register");
            CommandUtil.unregisterCommand(plugin,"l");
            CommandUtil.unregisterCommand(plugin,"login");
        }




    }
}
