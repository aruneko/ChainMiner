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

fun Block.findVein(): List<Block> {
    val paths = ArrayDeque<ArrayDeque<Block>>()
    val foundBlocks = ArrayDeque<Block>()
    var target = this
    while (true) {
        // まず対象になっているブロックを探索済みにする
        foundBlocks.addFirst(target)
        // 周囲の破壊対象ブロックを取得する
        val around = ArrayDeque(
            target.getAround().extractEquals(target).filterNot {
                // ただし探索済みおよび経路として追加済みのブロックは外す
                foundBlocks.contains(it) || paths.flatten().contains(it)
            }
        )
        // 周囲に何もない = 木構造の先端まで来たとき
        if (around.isEmpty()) {
            // 未探索のノードを取得
            val path = paths.removeFirstOrNull()
            if (path == null) {
                // 未探索のノードがなければ終了
                break
            } else {
                // 未探索の経路を持つノードがあればそのうちのひとつを次の対象とする
                target = path.removeFirst()
                if (path.isNotEmpty()) {
                    // ただし残りの経路は未探索のものとして残しておく
                    paths.addFirst(path)
                }
                continue
            }
        }
        // 木構造の途中にあるとき
        // 周囲のブロックからひとつ取ってくる
        target = around.removeFirst()
        if (around.isNotEmpty()) {
            // 周囲のブロックが残っていれば未探索の経路として登録
            paths.addFirst(around)
        }
    }
    return foundBlocks.toList()
}
