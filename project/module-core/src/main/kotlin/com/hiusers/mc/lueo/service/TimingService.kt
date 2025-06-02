package com.hiusers.mc.lueo.service

import taboolib.common.platform.Schedule

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
//        val luckAPI = Luck.api()
//        for (player in Bukkit.getOnlinePlayers()) {
//            if (luckAPI.checkResetTime(player)) {
//                luckAPI.resetLuck(player)
//            }
//        }
    }

}