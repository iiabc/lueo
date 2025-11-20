package com.hiusers.mc.lueo.api

import org.bukkit.entity.Player
import java.time.LocalDateTime
import java.util.UUID

/**
 * @author iiabc
 * @since 2025/6/1 20:38
 */
interface LuckAPI {

    /**
     * 创建玩家数据
     * @param player 玩家
     */
    fun createUser(player: Player)

    /**
     * 创建玩家数据
     * @param uuid 玩家UUID
     */
    fun createUser(uuid: UUID)

    /**
     * 获取玩家的幸运值
     * @param player 玩家
     * @return 幸运值
     */
    fun getLuck(player: Player): Int

    /**
     * 获取玩家的幸运值
     * @param uuid 玩家UUID
     * @return 幸运值
     */
    fun getLuck(uuid: UUID): Int

    /**
     * 设置玩家的幸运值
     * @param player 玩家
     * @param value 幸运值
     */
    fun setLuck(player: Player, value: Int): Int?

    /**
     * 设置玩家的幸运值
     * @param uuid 玩家UUID
     * @param value 幸运值
     */
    fun setLuck(uuid: UUID, value: Int): Int?

    /**
     * 增加玩家的幸运值
     * @param player 玩家
     * @param value 幸运值
     */
    fun addLuck(player: Player, value: Int): Int?

    /**
     * 增加玩家的幸运值
     * @param uuid 玩家UUID
     * @param value 幸运值
     */
    fun addLuck(uuid: UUID, value: Int): Int?

    /**
     * 减少玩家的幸运值
     * @param player 玩家
     * @param value 幸运值
     */
    fun delLuck(player: Player, value: Int): Int?

    /**
     * 减少玩家的幸运值
     * @param uuid 玩家UUID
     * @param value 幸运值
     */
    fun delLuck(uuid: UUID, value: Int): Int?

    /**
     * 重置玩家的幸运值
     * @param player 玩家
     * @return 是否重置成功
     */
    fun resetLuck(player: Player): Boolean

    /**
     * 重置玩家的幸运值
     * @param uuid 玩家UUID
     * @return 是否重置成功
     */
    fun resetLuck(uuid: UUID): Boolean

    /**
     * 获取玩家的幸运值重置时间
     * @param player 玩家
     * @return 幸运值重置时间
     */
    fun getResetTime(player: Player): LocalDateTime?

    /**
     * 获取玩家的幸运值重置时间
     * @param uuid 玩家UUID
     * @return 幸运值重置时间
     */
    fun getResetTime(uuid: UUID): LocalDateTime?

    /**
     * 检查玩家的幸运值重置时间
     * @param player 玩家
     * @return 是否需要重置
     */
    fun checkResetTime(player: Player): Boolean

    /**
     * 检查玩家的幸运值重置时间
     * @param uuid 玩家UUID
     * @return 是否需要重置
     */
    fun checkResetTime(uuid: UUID): Boolean

}