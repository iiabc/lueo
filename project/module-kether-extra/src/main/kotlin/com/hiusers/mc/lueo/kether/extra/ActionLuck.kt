package com.hiusers.mc.lueo.kether.extra

import com.hiusers.mc.lueo.LuckAPIProvider
import com.hiusers.mc.lueo.kether.ActionFrame.player
import taboolib.common5.cint
import taboolib.module.kether.KetherParser
import taboolib.module.kether.combinationParser
import taboolib.module.kether.run

object ActionLuck {

    /**
     * luck add {action}
     * luck del {action}
     * luck get
     */
    @KetherParser(["luck"], namespace = "Lueo", shared = true)
    fun parser() = combinationParser {
        it.group(
            symbol(),
            text(),
            command("to", then = action()).option().defaultsTo(null)
        ).apply(it) { action, key, value ->
            now {
                when (action.lowercase()) {
                    "add" -> {
                        val a = value?.let { v -> run(v).getNow(0) }?.cint?: 0
                        LuckAPIProvider.api().addLuck(player(), a)
                    }

                    "del" -> {
                        val a = value?.let { v -> run(v).getNow(0) }?.cint?: 0
                         LuckAPIProvider.api().delLuck(player(), a)
                    }

                    "get" -> {
                        LuckAPIProvider.api().getLuck(player())
                    }

                    else -> error("Unknown luck action: $action")
                }
            }
        }
    }

}