package top.touchstudio.cup.modules;

import org.bukkit.configuration.InvalidConfigurationException;
import top.touchstudio.cup.command.CupCommand;
import top.touchstudio.cup.CommonUsePlugin;
import top.touchstudio.cup.configs.ModuleConfig;
import top.touchstudio.cup.modules.back.BackCommand;
import top.touchstudio.cup.modules.bestgamamode.*;
import top.touchstudio.cup.modules.chainmining.ChainMiningCommand;
import top.touchstudio.cup.modules.chainmining.ChainMiningListener;
import top.touchstudio.cup.modules.deathhead.DeathHead;
import top.touchstudio.cup.modules.hat.HatCommand;
import top.touchstudio.cup.modules.home.HomeCommand;
import top.touchstudio.cup.modules.login.CommandInterceptor;
import top.touchstudio.cup.modules.login.PlayerActionListener;
import top.touchstudio.cup.modules.login.PlayerJoinListener;
import top.touchstudio.cup.modules.money.MoneyCommand;
import top.touchstudio.cup.modules.money.MoneyEvent;
import top.touchstudio.cup.modules.nightvision.NightVisionCommand;
import top.touchstudio.cup.modules.quit.QuitCommand;
import top.touchstudio.cup.modules.sneakspeedtree.SneakSpeedTreeListener;
import top.touchstudio.cup.modules.tpa.Tpa;
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
        ModuleList.add("tpa");
        ModuleList.add("hat");
        ModuleList.add("deathhead");
        ModuleList.add("back");
        ModuleList.add("money");
        ModuleList.add("bestgamemode");
        ModuleList.add("home");

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
            plugin.getServer().getPluginManager().registerEvents(new SneakSpeedTreeListener(), plugin);
        }
        //quit
        if (ModuleMap.get("quit")){
            plugin.getCommand("quit").setExecutor(new QuitCommand());
        }else {
            CommandUtil.unregisterCommand(plugin,"quit");
        }

        //tpa
        if (ModuleMap.get("tpa")){
            plugin.getCommand("tpa").setExecutor(new Tpa());
            plugin.getServer().getPluginManager().registerEvents(new Tpa(),plugin);
        }else {
            CommandUtil.unregisterCommand(plugin,"tpa");
        }

        //hat
        if (ModuleMap.get("hat")){
            plugin.getCommand("hat").setExecutor(new HatCommand());
        }else {
            CommandUtil.unregisterCommand(plugin,"hat");
        }

        //deathhead
        if (ModuleMap.get("deathhead")){
            plugin.getServer().getPluginManager().registerEvents(new DeathHead(),plugin);
        }

        //back
        if (ModuleMap.get("back")){
            plugin.getCommand("back").setExecutor(new BackCommand());
            plugin.getServer().getPluginManager().registerEvents(new BackCommand(),plugin);
        }else {
            CommandUtil.unregisterCommand(plugin,"back");
        }

        //money
        if (ModuleMap.get("money")){
            plugin.getServer().getPluginManager().registerEvents(new MoneyEvent(),plugin);
            plugin.getCommand("money").setExecutor(new MoneyCommand());
        }else {
            CommandUtil.unregisterCommand(plugin,"money");
        }



        //连锁挖矿
        if (ModuleMap.get("chainmining")){
            plugin.getServer().getPluginManager().registerEvents(new ChainMiningListener(plugin), plugin); // 传递插件实例
            plugin.getCommand("chainmining").setExecutor(new ChainMiningCommand(plugin));
            plugin.getCommand("cm").setExecutor(new ChainMiningCommand(plugin));
        }else {
            CommandUtil.unregisterCommand(plugin,"chainmining");
            CommandUtil.unregisterCommand(plugin,"cm");
        }


        //夜视
        if (ModuleMap.get("nightvision")){
            plugin.getCommand("nv").setExecutor(new NightVisionCommand());
            plugin.getCommand("nightvision").setExecutor(new NightVisionCommand());
            plugin.getCommand("夜视").setExecutor(new NightVisionCommand());
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

            plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(plugin), plugin);
            plugin.getServer().getPluginManager().registerEvents(new PlayerActionListener(), plugin);
            plugin.getServer().getPluginManager().registerEvents(new CommandInterceptor(plugin), plugin);
        }else {
            CommandUtil.unregisterCommand(plugin,"reg");
            CommandUtil.unregisterCommand(plugin,"register");
            CommandUtil.unregisterCommand(plugin,"l");
            CommandUtil.unregisterCommand(plugin,"login");
        }


        //bestgamemode [gm0|gm1|gm2]
        if (ModuleMap.get("bestgamemode")){
            plugin.getCommand("gm").setExecutor(new BestGamemode());

            plugin.getCommand("gm0").setExecutor(new Gm0());
            plugin.getCommand("生存模式").setExecutor(new Gm0());

            plugin.getCommand("gm1").setExecutor(new Gm1());
            plugin.getCommand("创造模式").setExecutor(new Gm1());

            plugin.getCommand("gm2").setExecutor(new Gm2());
            plugin.getCommand("冒险模式").setExecutor(new Gm2());

            plugin.getCommand("gm3").setExecutor(new Gm3());
            plugin.getCommand("旁观模式").setExecutor(new Gm3());

        }else {
            CommandUtil.unregisterCommand(plugin,"gm");

            CommandUtil.unregisterCommand(plugin,"gm0");
            CommandUtil.unregisterCommand(plugin,"生存模式");

            CommandUtil.unregisterCommand(plugin,"gm1");
            CommandUtil.unregisterCommand(plugin,"创造模式");

            CommandUtil.unregisterCommand(plugin,"gm2");
            CommandUtil.unregisterCommand(plugin,"冒险模式");

            CommandUtil.unregisterCommand(plugin,"gm3");
            CommandUtil.unregisterCommand(plugin,"旁观模式");


        }

        //home
        if (ModuleMap.get("home")){
            plugin.getCommand("home").setExecutor(new HomeCommand());
        }else {
            CommandUtil.unregisterCommand(plugin,"home");
        }
    }
}
