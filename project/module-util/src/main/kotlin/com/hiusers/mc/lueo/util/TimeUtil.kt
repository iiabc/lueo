package com.hiusers.mc.lueo.util

import java.time.LocalDateTime
import java.time.ZoneId

/**
 * @author iiabc
 * @since 2025/6/1 21:22
 */
object TimeUtil {
    fun LocalDateTime.toLong(): Long {
        return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}