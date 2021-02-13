package net.aruneko.chainminer.extensions

import org.bukkit.block.Block
import org.bukkit.entity.Player

fun List<Block>.extractEquals(block: Block) : List<Block> {
    return this.filter { it.type === block.type }
}

fun List<Block>.sortByDistance(player: Player): List<Block> {
    return this.sortedBy { player.location.distance(it.location) }
}
