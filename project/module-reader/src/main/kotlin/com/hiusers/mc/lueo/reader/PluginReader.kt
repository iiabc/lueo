package com.hiusers.mc.lueo.reader

import org.bukkit.Bukkit

object PluginReader {

    fun hasPlugin(name: String) = Bukkit.getPluginManager().getPlugin(name) != null

}