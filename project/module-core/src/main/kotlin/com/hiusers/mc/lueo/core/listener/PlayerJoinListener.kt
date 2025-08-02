package com.hiusers.mc.lueo.core.listener

import com.hiusers.mc.lueo.Lueo
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.sendLang

/**
 * @author iiabc
 * @since 2025/6/1 20:59
 */
object PlayerJoinListener {

    @SubscribeEvent
    fun event(ev: PlayerJoinEvent) {
        val player = ev.player
        val luckAPI = Lueo.api()
        luckAPI.createUser(player)
        if (luckAPI.checkResetTime(player)) {
            if (luckAPI.resetLuck(player)) return
            player.sendLang("error_data")
        }
    }

}