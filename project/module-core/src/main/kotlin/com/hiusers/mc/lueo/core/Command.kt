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
        literal("set") {
            int("amount") {
                player {
                    execute<ProxyCommandSender> { sender, context, _ ->
                        sender.runWithPlayer(context) {
                            val amount = context["amount"].cint
                            val value = LuckAPIProvider.api().setLuck(this, amount)?: "错误"
                            sendLang("receive_luck_set", value)
                            sender.sendLang("admin_luck_set", value, name)
                        }
                    }
                }
                execute<Player> { sender, context, _ ->
                    val amount = context["amount"].cint
                    val value = LuckAPIProvider.api().setLuck(sender, amount)?: "错误"
                    sender.sendLang("receive_luck_set", value)
                }
            }
        }
        literal("add") {
            int("amount") {
                player {
                    execute<ProxyCommandSender> { sender, context, _ ->
                        sender.runWithPlayer(context) {
                            val amount = context["amount"].cint
                            val value = LuckAPIProvider.api().addLuck(this, amount)?: "错误"
                            sendLang("receive_luck_add", amount, value)
                            sender.sendLang("admin_luck_add", amount, value, name)
                        }
                    }
                }
                execute<Player> { sender, context, _ ->
                    val amount = context["amount"].cint
                    val value = LuckAPIProvider.api().addLuck(sender, amount)?: "错误"
                    sender.sendLang("receive_luck_add", amount, value)
                }
            }
        }
        literal("del") {
            int("amount") {
                player {
                    execute<ProxyCommandSender> { sender, context, _ ->
                        sender.runWithPlayer(context) {
                            val amount = context["amount"].cint
                            val value = LuckAPIProvider.api().delLuck(this, amount)?: "错误"
                            sendLang("receive_luck_del", amount, value)
                            sender.sendLang("admin_luck_del", amount, value, name)
                        }
                    }
                }
                execute<Player> { sender, context, _ ->
                    val amount = context["amount"].cint
                    val value = LuckAPIProvider.api().delLuck(sender, amount)?: "错误"
                    sender.sendLang("receive_luck_del", amount, value)
                }
            }
        }
        literal("get") {
            player {
                execute<ProxyCommandSender> { sender, context, _ ->
                    sender.runWithPlayer(context) {
                        val value = LuckAPIProvider.api().getLuck(this)?: "错误"
                        sender.sendLang("admin_luck_look", value)
                    }
                }
            }
            execute<Player> { sender, _, _ ->
                val value = LuckAPIProvider.api().getLuck(sender)?: "错误"
                sender.sendLang("admin_luck_look_self", value)
            }
        }
    }

}