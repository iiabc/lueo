package com.hiusers.mc.lueo.core.load

import com.hiusers.mc.lueo.LuckAPIProvider
import com.hiusers.mc.lueo.core.impl.LuckAPIImpl
import com.hiusers.mc.lueo.system.Loadable
import com.hiusers.mc.lueo.system.Register
import taboolib.common.platform.function.info

/**
 * @author iiabc
 * @since 2025/6/2 13:58
 */
@Register
object LueoAPILoader : Loadable {
    override val priority: Int
        get() = 1

    override fun load() {
        info("加载 api>>>>>>>>>>>>>>>>>>>>>>")
        LuckAPIProvider.register(LuckAPIImpl())
    }
}