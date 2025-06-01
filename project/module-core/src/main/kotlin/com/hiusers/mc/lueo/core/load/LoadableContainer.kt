package com.hiusers.mc.lueo.core.load

import com.hiusers.mc.lueo.system.BaseListContainer
import com.hiusers.mc.lueo.system.Loadable
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

object LoadableContainer:  BaseListContainer<Loadable>() {

    @Awake(LifeCycle.ENABLE)
    fun load() {
        getAll().sortedBy {
            it.priority
        }.forEach { it.load() }
    }

    @Awake(LifeCycle.DISABLE)
    fun unload() {
        // 按倒序卸载
        getAll().reversed().forEach {
            it.unload()
        }
    }

    fun reset() {
        unload()
        load()
    }

}