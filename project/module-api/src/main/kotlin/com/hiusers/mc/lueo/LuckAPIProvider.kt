package com.hiusers.mc.lueo

import com.hiusers.mc.lueo.api.LuckAPI
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import taboolib.platform.util.bukkitPlugin

/**
 * @author iiabc
 * @since 2025/6/2 13:41
 */
object LuckAPIProvider {

    internal var api: LuckAPI? = null

    fun api(): LuckAPI {
        return api ?: error("LuckAPI 未完成加载")
    }

    fun register(api: LuckAPI) {
        this.api = api
        Bukkit.getServicesManager().register(
            LuckAPI::class.java,
            api,
            bukkitPlugin,
            ServicePriority.Normal
        )
    }

}