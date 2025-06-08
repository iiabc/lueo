package com.hiusers.mc.lueo.repository

import com.hiusers.mc.lueo.database.table.PlayerLuckTable
import com.hiusers.mc.lueo.database.table.PlayerLuckTable.lastReset
import com.hiusers.mc.lueo.database.table.PlayerLuckTable.luck
import com.hiusers.mc.lueo.reader.ConfigReader
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime
import java.util.UUID

/**
 * @author iiabc
 * @since 2025/6/1 20:12
 */
object PlayerLuckRepository {

    fun existPlayer(uuid: UUID): Boolean {
        return PlayerLuckTable.select(PlayerLuckTable.id).where {
            PlayerLuckTable.id eq uuid
        }.limit(1).count() > 0
    }

    /**
     * 为指定UUID的玩家创建新条目
     * 如果玩家已存在，则不执行任何操作
     * @param uuid 玩家的UUID
     */
    fun createUser(uuid: UUID) {
        transaction {
            if (!existPlayer(uuid)) {
                PlayerLuckTable.insert {
                    it[id] = uuid
                    it[luck] = ConfigReader.defaultLuck
                }
            }
        }
    }

    /**
     * 更新玩家的幸运值
     * 新值会被限制在最大值以内
     * @param uuid 玩家的UUID
     * @param value 新的幸运值
     * @return 玩家更新后的实际幸运值。如果玩家不存在或更新失败，则返回 null
     */
    fun updateLuck(uuid: UUID, value: Int): Int? {
        return transaction {
            // 计算出实际将要更新的值
            val actualNewLuck = value.coerceAtMost(ConfigReader.maxLuck)

            val updatedRows = PlayerLuckTable.update({ PlayerLuckTable.id eq uuid }) {
                it[luck] = actualNewLuck
            }

            if (updatedRows > 0) {
                actualNewLuck
            } else {
                null
            }
        }
    }

    /**
     * 获取玩家当前的幸运值
     * @param uuid 玩家的UUID
     * @return 玩家的幸运值，如果玩家不存在则返回 null
     */
    fun getLuck(uuid: UUID): Int? {
        return transaction {
            PlayerLuckTable.select(luck).where { PlayerLuckTable.id eq uuid }.singleOrNull()?.get(luck)
        }
    }

    /**
     * 为玩家当前的幸运值增加指定数值，结果值会被限制在的最大值以内
     * @param uuid 玩家的UUID
     * @param value 要增加的数值。可以是负数以减少幸运值
     * @return 添加操作后的新幸运值，如果玩家不存在则返回 null
     */
    fun addLuck(uuid: UUID, value: Int): Int? {
        return transaction {
            // 在同一事务中获取当前幸运值，确保数据一致性
            val currentLuck = getLuck(uuid)
            if (currentLuck != null) {
                // 计算新的幸运值，并进行限制
                val newLuck = (currentLuck + value).coerceAtMost(ConfigReader.maxLuck)
                // 执行更新操作
                val updatedRows = PlayerLuckTable.update({ PlayerLuckTable.id eq uuid }) {
                    it[luck] = newLuck
                }
                // 如果更新成功，返回新的幸运值，否则返回 null
                if (updatedRows > 0) newLuck else null
            } else {
                null
            }
        }
    }

    /**
     * 减少玩家的幸运值
     * @param uuid 玩家的UUID
     * @param value 要减少的数值
     * @return 减少操作后的新幸运值，如果玩家不存在则返回 null。
     */
    fun delLuck(uuid: UUID, value: Int): Int? {
        return addLuck(uuid, -value)
    }

    /**
     * 将玩家的幸运值重置为默认值，并更新上次重置时间为当前时间
     * @param uuid 玩家的UUID
     * @return 是否重置成功
     */
    fun resetLuck(uuid: UUID): Boolean {
        val rowNumber = transaction {
            PlayerLuckTable.update({ PlayerLuckTable.id eq uuid }) {
                it[luck] = ConfigReader.defaultLuck
                it[lastReset] = LocalDateTime.now()
            }
        }
        return rowNumber > 0
    }

    /**
     * 获取玩家上次重置幸运值的时间。
     * @param uuid 玩家的UUID
     * @return 上次重置的时间，如果玩家未找到或从未重置则返回 null
     */
    fun getResetTime(uuid: UUID): LocalDateTime? {
        return transaction {
            PlayerLuckTable.select(lastReset).where { PlayerLuckTable.id eq uuid }.singleOrNull()?.get(lastReset)
        }
    }

}