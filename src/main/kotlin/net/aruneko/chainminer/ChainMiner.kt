package net.aruneko.chainminer

import net.aruneko.chainminer.listeners.ChainMinerListener
import org.bukkit.plugin.java.JavaPlugin

class ChainMiner : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(ChainMinerListener(this, server), this)
    }

    override fun onDisable() {}
}
