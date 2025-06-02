package com.hiusers.mc.lueo.core

import com.hiusers.mc.lueo.LuckAPIProvider
import com.hiusers.mc.lueo.core.load.LoadableContainer
import com.hiusers.mc.lueo.util.BukkitSimple.runWithPlayer
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.int
import taboolib.common.platform.command.player
import taboolib.common.platform.command.subCommand
import taboolib.common5.cint
import taboolib.module.lang.sendLang
import taboolib.platform.util.sendLang

@CommandHeader("lueo", permission = "lueo.command")
object Command {

    @CommandBody(permission = "lueo.command.admin.reload")
    val reload = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            LoadableContainer.reset()
            sender.sendLang("command_reload")
        }
    }

    @CommandBody(permission = "lueo.command.admin.luck")
    val luck = subCommand {
        literal("add") {
            int("amount") {
                player {
                    execute<ProxyCommandSender> { sender, context, _ ->
                        sender.runWithPlayer(context) {
                            val value = LuckAPIProvider.api().addLuck(this, context["amount"].cint)
                            sendLang("receive_luck_add", value)
                            sender.sendLang("admin_luck_add", value, name)
                        }
                    }
                }
                execute<Player> { sender, context, _ ->
                    val value = LuckAPIProvider.api().addLuck(sender, context["amount"].cint)
                    sender.sendLang("receive_luck_add", value)
                }
            }
        }
        literal("del") {
            int("amount") {
                player {
                    execute<ProxyCommandSender> { sender, context, _ ->
                        sender.runWithPlayer(context) {
                            val value = LuckAPIProvider.api().delLuck(this, context["amount"].cint)
                            sendLang("receive_luck_del", value)
                            sender.sendLang("admin_luck_del", value, name)
                        }
                    }
                }
                execute<Player> { sender, context, _ ->
                    val value = LuckAPIProvider.api().delLuck(sender, context["amount"].cint)
                    sender.sendLang("receive_luck_del", value)
                }
            }
        }
    }

}