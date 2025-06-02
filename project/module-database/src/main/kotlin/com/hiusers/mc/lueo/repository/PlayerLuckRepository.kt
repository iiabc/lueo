package com.hiusers.mc.lueo.repository

import com.hiusers.mc.lueo.database.entity.PlayerLuckEntity
import com.hiusers.mc.lueo.reader.ConfigReader
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.time.LocalDateTime
import java.util.UUID

/**
 * @author iiabc
 * @since 2025/6/1 20:12
 */
object PlayerLuckRepository {

    fun findOrCreate(uuid: UUID): PlayerLuckEntity = transaction {
        PlayerLuckEntity.findById(uuid) ?: PlayerLuckEntity.new(uuid) {
            luck = ConfigReader.defaultLuck
            lastReset = null
        }
    }

    fun updateValue(uuid: UUID, value: Int): Int = transaction {
        val playerLuck = findOrCreate(uuid)
        playerLuck.luck = value.coerceAtMost(ConfigReader.maxLuck)
        playerLuck.luck
    }

    fun addValue(uuid: UUID, value: Int): Int = transaction {
        val playerLuck = findOrCreate(uuid)
        playerLuck.luck = (playerLuck.luck + value).coerceAtMost(ConfigReader.maxLuck)
        playerLuck.luck
    }

    fun delValue(uuid: UUID, value: Int): Int = transaction {
        val playerLuck = findOrCreate(uuid)
        playerLuck.luck = (playerLuck.luck - value)
        playerLuck.luck
    }

    fun resetLuck(uuid: UUID): Boolean = transaction {
        val playerLuck = findOrCreate(uuid)
        playerLuck.lastReset = LocalDateTime.now()
        true
    }

    fun getResetTime(uuid: UUID): LocalDateTime? = transaction {
        val playerLuck = findOrCreate(uuid)
        playerLuck.lastReset
    }

}