package net.aruneko.chainminer.extensions

import org.bukkit.Material
import org.bukkit.block.Block

fun Block.getAround(): List<Block> {
    val world = this.world
    val blockX = this.x
    val blockY = this.y
    val blockZ = this.z

    val xs = listOf(blockX - 1, blockX, blockX + 1)
    val ys = listOf(blockY - 1, blockY, blockY + 1)
    val zs = listOf(blockZ - 1, blockZ, blockZ + 1)

    return xs.flatMap { x ->
        ys.flatMap {
            y -> zs.map { z -> world.getBlockAt(x, y, z) }
        }
    }.filter { !(it.x == blockX && it.y == blockY && it.z == blockX) }
}

fun Block.isOre(): Boolean {
    val ores = listOf(
        Material.REDSTONE_ORE,
        Material.NETHER_GOLD_ORE,
        Material.NETHER_QUARTZ_ORE,
        Material.LAPIS_ORE,
        Material.IRON_ORE,
        Material.GOLD_ORE,
        Material.EMERALD_ORE,
        Material.DIAMOND_ORE,
        Material.COAL_ORE,
        Material.ANCIENT_DEBRIS,
    )
    return ores.contains(this.type)
}

fun Block.findVein(foundOres: List<Block>): List<Block> {
    val around = this.getAround().extractEquals(this).filter { !foundOres.map { f -> f.type }.contains(it.type) }
    return if (around.isEmpty()) {
        foundOres
    } else {
        around.flatMap { it.findVein(around) }
    }
}
