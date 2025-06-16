package com.hiusers.mc.lueo.service

import com.hiusers.mc.lueo.LuckAPIProvider
import taboolib.common.platform.Schedule
import taboolib.platform.util.onlinePlayers

/**
 * @author iiabc
 * @since 2025/6/2 01:58
 */
object TimingService {

    /**
     * 每小时检查是否需要重置幸运值
     */
    @Schedule(true, 50, 20 * 60 * 60)
    fun checkResetTime() {
        val luckAPI = LuckAPIProvider.api()
        onlinePlayers.forEach {
            if (luckAPI.checkResetTime(it)) {
                luckAPI.resetLuck(it)
            }
        }
    }

}