package com.hiusers.mc.lueo.service

import com.hiusers.mc.lueo.api.LuckAPI
import org.bukkit.Bukkit
import taboolib.common.platform.Schedule

/**
 * @author iiabc
 * @since 2025/6/2 01:58
 */
object TimingService {

    private val luckAPI = LuckAPI.INSTANCE

    /**
     * 每小时检查是否需要重置幸运值
     */
    @Schedule(true, period = 20 * 60 * 60)
    fun checkResetTime() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (luckAPI.checkResetTime(player)) {
                luckAPI.resetLuck(player)
            }
        }
    }

}