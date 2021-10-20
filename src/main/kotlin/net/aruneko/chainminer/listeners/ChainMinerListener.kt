package net.aruneko.chainminer.listeners

import net.aruneko.chainminer.extensions.findVein
import net.aruneko.chainminer.extensions.isOre
import net.aruneko.chainminer.extensions.sortByDistance
import org.bukkit.Server
import org.bukkit.Tag.MINEABLE_PICKAXE
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.Plugin

class ChainMinerListener(private val plugin: Plugin, private val server: Server) : Listener {
    @EventHandler
    fun onBreakOre(event: BlockBreakEvent) {
        val player = event.player
        val mainHandItem = player.inventory.itemInMainHand

        if (!player.isSneaking) {
            // スニークしてなかったらやめる
            return
        }

        val block = event.block

        if (!block.isPreferredTool(mainHandItem) || !MINEABLE_PICKAXE.isTagged(block.type)) {
            // 適正ツールじゃなかったらやめる
            return
        }

        if (!block.isOre()) {
            // 鉱石じゃなかったらやめる
            return
        }

        val vein = block.findVein()
        vein.sortByDistance(player).reversed().forEach { it.breakNaturally(mainHandItem) }
    }
}
