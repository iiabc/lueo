package com.hiusers.mc.lueo.core.load

import com.hiusers.mc.lueo.Lueo
import com.hiusers.mc.lueo.core.impl.LuckAPIImpl
import com.hiusers.mc.lueo.system.Loadable
import com.hiusers.mc.lueo.system.Register

/**
 * @author iiabc
 * @since 2025/6/2 13:58
 */
@Register
object LueoAPILoader : Loadable {
    override val priority: Int
        get() = 1

    override fun load() {
        Lueo.register(LuckAPIImpl())
    }
}