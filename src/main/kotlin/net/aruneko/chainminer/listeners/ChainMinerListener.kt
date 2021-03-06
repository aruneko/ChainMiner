package net.aruneko.chainminer.listeners

import net.aruneko.chainminer.extensions.findVein
import net.aruneko.chainminer.extensions.isOre
import net.aruneko.chainminer.extensions.isPickaxe
import net.aruneko.chainminer.extensions.sortByDistance
import org.bukkit.Server
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
            return
        }

        if (!mainHandItem.isPickaxe()) {
            return
        }

        val block = event.block

        if (!block.isOre()) {
            return
        }

        val vein = block.findVein()
        vein.sortByDistance(player).reversed().forEach { it.breakNaturally(mainHandItem) }
    }
}
