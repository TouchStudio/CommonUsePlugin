package top.touchstudio.cup.modules.sneakspeedtree;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import top.touchstudio.cup.utils.ChatUtil;

import static top.touchstudio.cup.modules.ModuleManager.ModuleMap;

public class SneakSpeedTreeListener implements Listener {

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e){
        Player player = e.getPlayer();
        if (ModuleMap.get("sneakspeedtree")) {
            if(e.isSneaking()){
                Block block = player.getLocation().getBlock().getRelative(0, 0, 0);

                if (block.getType() == Material.OAK_SAPLING || block.getType() == Material.BIRCH_SAPLING || block.getType() == Material.SPRUCE_SAPLING || block.getType() == Material.JUNGLE_SAPLING || block.getType() == Material.ACACIA_SAPLING || block.getType() == Material.DARK_OAK_SAPLING) {
                    ChatUtil.pluginSay(player, "&b树苗正在加速生长!");
                    block.applyBoneMeal(BlockFace.UP);
                }
            }
        }

    }
}
