package top.touchstudio.cup.modules.ChainMining;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import top.touchstudio.cup.CommonUsePlugin;

import java.util.HashSet;
import java.util.Set;

public class ChainMining implements Listener {

    private final CommonUsePlugin plugin;

    public ChainMining(CommonUsePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!plugin.isChainMiningEnabled()) {
            return;
        }

        Block block = event.getBlock();
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();

        if (isValidTool(tool) && isOre(block.getType())) {
            Set<Block> blocksToBreak = new HashSet<>();
            findBlocksToBreak(block, block.getType(), blocksToBreak);

            for (Block b : blocksToBreak) {
                if (isValidTool(tool) && isOre(b.getType())) {
                    b.breakNaturally(tool);
                }
            }

            int blocksBroken = blocksToBreak.size();
            int newDurability = tool.getDurability() + blocksBroken;

            if (newDurability >= tool.getType().getMaxDurability()) {
                event.getPlayer().getInventory().remove(tool);
            } else {
                tool.setDurability((short) newDurability);
            }
        }
    }

    private boolean isValidTool(ItemStack tool) {
        return tool != null && tool.getType().name().endsWith("_PICKAXE");
    }

    private boolean isOre(Material material) {
        return material == Material.COAL_ORE || material == Material.IRON_ORE || material == Material.GOLD_ORE
                || material == Material.DIAMOND_ORE || material == Material.EMERALD_ORE || material == Material.REDSTONE_ORE
                || material == Material.LAPIS_ORE || material == Material.NETHER_QUARTZ_ORE || material == Material.NETHER_GOLD_ORE
                || material == Material.DEEPSLATE_COAL_ORE || material == Material.DEEPSLATE_IRON_ORE || material == Material.DEEPSLATE_GOLD_ORE
                || material == Material.DEEPSLATE_DIAMOND_ORE || material == Material.DEEPSLATE_EMERALD_ORE || material == Material.DEEPSLATE_REDSTONE_ORE
                || material == Material.DEEPSLATE_LAPIS_ORE || material == Material.ANCIENT_DEBRIS || material == Material.COPPER_ORE;
    }

    private void findBlocksToBreak(Block start, Material type, Set<Block> blocksToBreak) {
        if (blocksToBreak.size() > 100) return; // 不挖超过100
        blocksToBreak.add(start);

        for (Block b : getAdjacentBlocks(start)) {
            if (!blocksToBreak.contains(b) && b.getType() == type) {
                findBlocksToBreak(b, type, blocksToBreak);
            }
        }
    }

    private Set<Block> getAdjacentBlocks(Block block) {
        Set<Block> adjacentBlocks = new HashSet<>();

        adjacentBlocks.add(block.getRelative(BlockFace.NORTH));
        adjacentBlocks.add(block.getRelative(BlockFace.SOUTH));
        adjacentBlocks.add(block.getRelative(BlockFace.EAST));
        adjacentBlocks.add(block.getRelative(BlockFace.WEST));
        adjacentBlocks.add(block.getRelative(BlockFace.UP));
        adjacentBlocks.add(block.getRelative(BlockFace.DOWN));

        adjacentBlocks.add(block.getRelative(BlockFace.NORTH_EAST));
        adjacentBlocks.add(block.getRelative(BlockFace.NORTH_WEST));
        adjacentBlocks.add(block.getRelative(BlockFace.SOUTH_EAST));
        adjacentBlocks.add(block.getRelative(BlockFace.SOUTH_WEST));
        return adjacentBlocks;
    }
}
