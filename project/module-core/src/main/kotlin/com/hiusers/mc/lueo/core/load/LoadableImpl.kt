package com.hiusers.mc.lueo.core.load

import com.hiusers.mc.lueo.system.Loadable
import com.hiusers.mc.lueo.system.Register
import taboolib.common.LifeCycle
import taboolib.common.inject.ClassVisitor
import taboolib.common.platform.Awake
import taboolib.library.reflex.ReflexClass

/**
 * @author iiabc
 * @since 2025/5/7 11:10
 */
@Awake
object LoadableImpl: ClassVisitor(1) {

    override fun getLifeCycle(): LifeCycle {
        return LifeCycle.LOAD
    }

    override fun visitStart(clazz: ReflexClass) {
        if (clazz.hasAnnotation(Register::class.java)) {
            LoadableContainer.add(clazz.getInstance()  as Loadable)
        }
    }

}