package com.hiusers.mc.lueo.core.listener

import com.hiusers.mc.lueo.Lueo
import com.hiusers.mc.lueo.reader.ConfigReader
import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.common.platform.event.SubscribeEvent

/**
 * 玩家死亡监听器
 * 每次死亡扣除配置的幸运值
 * 
 * @author iiabc
 * @since 2025/1/XX
 */
object PlayerDeathListener {

    @SubscribeEvent
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity
        val luckAPI = Lueo.api()
        
        // 扣除幸运值
        val penalty = ConfigReader.deathPenalty
        if (penalty > 0) {
            luckAPI.delLuck(player, penalty)
        }
    }

}
