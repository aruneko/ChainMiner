package net.aruneko.chainminer.extensions

import org.bukkit.Material
import org.bukkit.block.Block

fun Block.getAround(): List<Block> {
    val world = this.world
    val x = this.x
    val y = this.y
    val z = this.z

    return listOf(
        world.getBlockAt(x - 1, y + 1, z - 1),
        world.getBlockAt(x, y + 1, z - 1),
        world.getBlockAt(x + 1, y + 1, z - 1),
        world.getBlockAt(x - 1, y + 1, z),
        world.getBlockAt(x, y + 1, z),
        world.getBlockAt(x + 1, y + 1, z),
        world.getBlockAt(x - 1, y + 1, z + 1),
        world.getBlockAt(x, y + 1, z + 1),
        world.getBlockAt(x + 1, y + 1, z + 1),
        world.getBlockAt(x - 1, y, z - 1),
        world.getBlockAt(x, y, z - 1),
        world.getBlockAt(x + 1, y, z - 1),
        world.getBlockAt(x - 1, y, z),
        world.getBlockAt(x + 1, y, z),
        world.getBlockAt(x - 1, y, z + 1),
        world.getBlockAt(x, y, z + 1),
        world.getBlockAt(x + 1, y, z + 1),
        world.getBlockAt(x - 1, y - 1, z - 1),
        world.getBlockAt(x, y - 1, z - 1),
        world.getBlockAt(x + 1, y - 1, z - 1),
        world.getBlockAt(x - 1, y - 1, z),
        world.getBlockAt(x, y - 1, z),
        world.getBlockAt(x + 1, y - 1, z),
        world.getBlockAt(x - 1, y - 1, z + 1),
        world.getBlockAt(x, y - 1, z + 1),
        world.getBlockAt(x + 1, y - 1, z + 1),
    )
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
