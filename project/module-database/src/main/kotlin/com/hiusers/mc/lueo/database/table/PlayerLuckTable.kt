package com.hiusers.mc.lueo.database.table

import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import org.jetbrains.exposed.v1.javatime.datetime

/**
 * @author iiabc
 * @since 2025/6/1 19:30
 */
object PlayerLuckTable : UUIDTable("player_luck") {

    val luck = integer("luck").default(0)
    val lastReset = datetime("last_reset").nullable()

}