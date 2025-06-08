package com.hiusers.mc.lueo.database.entity

import com.hiusers.mc.lueo.database.table.PlayerLuckTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

/**
 * @author iiabc
 * @since 2025/6/1 19:38
 */
class PlayerLuckEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PlayerLuckEntity>(PlayerLuckTable)

    var luck by PlayerLuckTable.luck
    var lastReset by PlayerLuckTable.lastReset
}
