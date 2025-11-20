package com.hiusers.mc.lueo.kether.extra

import com.hiusers.mc.lueo.Lueo
import com.hiusers.mc.lueo.kether.ActionFrame.player
import taboolib.common5.cint
import taboolib.module.kether.KetherParser
import taboolib.module.kether.combinationParser
import taboolib.module.kether.run
import java.util.UUID

object ActionLuck {

    /** 
     * luck add {action} [with {uuid}]
     * luck del {action} [with {uuid}]
     * luck get value [with {uuid}]
     */
    @KetherParser(["luck"], namespace = "Lueo", shared = true)
    fun parser() = combinationParser {
        it.group(
            command("add", then = int()).option(),
            command("del", then = int()).option(),
            command("get", then = text()).option(),
            command("with", then = text()).option()
        ).apply(it) { add, del, get, with ->
            now {
                if (add != null) {
                    val uuid = with?.let { uuid -> UUID.fromString(uuid) } ?: player().uniqueId
                    Lueo.api().addLuck(uuid, add)
                }else if (del != null) {
                    val uuid = with?.let { uuid -> UUID.fromString(uuid) } ?: player().uniqueId
                    Lueo.api().delLuck(uuid, del)
                }else if (get != null) {
                    if (get == "value") {
                        val uuid = with?.let { uuid -> UUID.fromString(uuid) } ?: player().uniqueId
                        Lueo.api().getLuck(uuid)
                    }else {
                        error("Unknown luck get: $get")
                    }
                }else {
                    error("Unknown luck action")
                }
            }
        }
        /*it.group(
            symbol(), 
            text(), 
            command("to", then = action()).option().defaultsTo(null)
        ).apply(it) { action, key, value ->
            now {
                when (action.lowercase()) {
                    "add" -> {
                        val a = value?.let { v -> run(v).getNow(0) }?.cint ?: 0
                        Lueo.api().addLuck(player(), a)
                    }
                    "del" -> {
                        val a = value?.let { v -> run(v).getNow(0) }?.cint ?: 0
                        Lueo.api().delLuck(player(), a)
                    }
                    "get" -> {
                        Lueo.api().getLuck(player())
                    }
                    else -> error("Unknown luck action: $action")
                }
            }
        }*/
    }
}
