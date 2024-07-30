package top.touchstudio.cup.modules.sneakspeedtree;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class SneakSpeedTreeListener implements Listener {

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e){
        Player player = e.getPlayer();
        if(e.isSneaking()){
            Block block = player.getLocation().getBlock().getRelative(0, 0, 0);

            if (block.getType() == Material.OAK_SAPLING || block.getType() == Material.BIRCH_SAPLING || block.getType() == Material.SPRUCE_SAPLING || block.getType() == Material.JUNGLE_SAPLING || block.getType() == Material.ACACIA_SAPLING || block.getType() == Material.DARK_OAK_SAPLING) {
                player.sendMessage("§a你正在加速树苗生长!");
                block.applyBoneMeal(BlockFace.UP);
            }
        }
    }
}
