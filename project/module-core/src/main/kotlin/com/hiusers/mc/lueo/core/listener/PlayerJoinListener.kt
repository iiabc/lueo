package com.hiusers.mc.lueo.core.listener

import com.hiusers.mc.lueo.LuckAPIProvider
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.SubscribeEvent

/**
 * @author iiabc
 * @since 2025/6/1 20:59
 */
object PlayerJoinListener {

    @SubscribeEvent
    fun event(ev: PlayerJoinEvent) {
        val player = ev.player
        val luckAPI = LuckAPIProvider.api()
        if (luckAPI.checkResetTime(player)) {
            luckAPI.resetLuck(player)
        }
    }

}