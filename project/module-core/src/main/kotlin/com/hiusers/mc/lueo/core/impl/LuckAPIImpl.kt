package com.hiusers.mc.lueo.core.impl

import com.hiusers.mc.lueo.api.LuckAPI
import com.hiusers.mc.lueo.reader.ConfigReader
import com.hiusers.mc.lueo.repository.PlayerLuckRepository
import com.hiusers.mc.lueo.util.TimeUtil.toLong
import org.bukkit.entity.Player
import taboolib.common5.util.parseTimeCycle
import java.time.LocalDateTime

/**
 * @author iiabc
 * @since 2025/6/1 20:41
 */
class LuckAPIImpl : LuckAPI {

    override fun createUser(player: Player) {
        PlayerLuckRepository.createUser(player.uniqueId)
    }

    override fun getLuck(player: Player): Int? {
        return PlayerLuckRepository.getLuck(player.uniqueId)
    }

    override fun setLuck(player: Player, value: Int): Int? {
        return PlayerLuckRepository.updateLuck(player.uniqueId, value)
    }

    override fun addLuck(player: Player, value: Int): Int? {
        return PlayerLuckRepository.addLuck(player.uniqueId, value)
    }

    override fun delLuck(player: Player, value: Int): Int? {
        return PlayerLuckRepository.delLuck(player.uniqueId, value)
    }

    override fun resetLuck(player: Player): Boolean {
        return PlayerLuckRepository.resetLuck(player.uniqueId)
    }

    override fun getResetTime(player: Player): LocalDateTime? {
        return PlayerLuckRepository.getResetTime(player.uniqueId)
    }

    override fun checkResetTime(player: Player): Boolean {
        val resetTime = getResetTime(player)
        if (resetTime == null) {
            return false
        }
        val timestamp = resetTime.toLong()
        val cycle = ConfigReader.resetTime.parseTimeCycle().start(timestamp) ?: return false
        return cycle.isTimeout
    }

}