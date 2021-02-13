package net.aruneko.chainminer.extensions

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun ItemStack.isPickaxe(): Boolean {
    val pickaxes = listOf(
        Material.WOODEN_PICKAXE,
        Material.STONE_PICKAXE,
        Material.IRON_PICKAXE,
        Material.GOLDEN_PICKAXE,
        Material.DIAMOND_PICKAXE,
        Material.NETHERITE_PICKAXE,
    )
    return pickaxes.contains(this.type)
}
