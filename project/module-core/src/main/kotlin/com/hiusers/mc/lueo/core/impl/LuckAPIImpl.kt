package com.hiusers.mc.lueo.core.impl

import com.hiusers.mc.lueo.api.LuckAPI
import com.hiusers.mc.lueo.reader.ConfigReader
import com.hiusers.mc.lueo.repository.PlayerLuckRepository
import com.hiusers.mc.lueo.util.TimeUtil.toLong
import org.bukkit.entity.Player
import taboolib.common5.util.parseTimeCycle
import java.time.LocalDateTime
import java.util.UUID

/**
 * @author iiabc
 * @since 2025/6/1 20:41
 */
class LuckAPIImpl : LuckAPI {

    override fun createUser(player: Player) = createUser(player.uniqueId)

    override fun createUser(uuid: UUID) {
        PlayerLuckRepository.createUser(uuid)
    }

    override fun getLuck(player: Player): Int = getLuck(player.uniqueId)

    override fun getLuck(uuid: UUID): Int {
        return PlayerLuckRepository.getLuck(uuid)?: 0
    }

    override fun setLuck(player: Player, value: Int): Int? = setLuck(player.uniqueId, value)

    override fun setLuck(uuid: UUID, value: Int): Int? {
        return PlayerLuckRepository.updateLuck(uuid, value)
    }

    override fun addLuck(player: Player, value: Int): Int? = addLuck(player.uniqueId, value)

    override fun addLuck(uuid: UUID, value: Int): Int? {
        return PlayerLuckRepository.addLuck(uuid, value)
    }

    override fun delLuck(player: Player, value: Int): Int? = delLuck(player.uniqueId, value)

    override fun delLuck(uuid: UUID, value: Int): Int? {
        return PlayerLuckRepository.delLuck(uuid, value)
    }

    override fun resetLuck(player: Player): Boolean = resetLuck(player.uniqueId)

    override fun resetLuck(uuid: UUID): Boolean {
        return PlayerLuckRepository.resetLuck(uuid)
    }

    override fun getResetTime(player: Player): LocalDateTime? = getResetTime(player.uniqueId)

    override fun getResetTime(uuid: UUID): LocalDateTime? {
        return PlayerLuckRepository.getResetTime(uuid)
    }

    override fun checkResetTime(player: Player): Boolean = checkResetTime(player.uniqueId)

    override fun checkResetTime(uuid: UUID): Boolean {
        val resetTime = getResetTime(uuid)
        if (resetTime == null) {
            return false
        }
        val timestamp = resetTime.toLong()
        val cycle = ConfigReader.resetTime.parseTimeCycle().start(timestamp) ?: return false
        return cycle.isTimeout
    }

}