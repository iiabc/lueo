package com.hiusers.mc.lueo.util

import taboolib.module.chat.component

/**
 * @author iiabc
 * @since 2025/5/28 19:21
 */
object ComponentUtil {

    fun String.toRawMessage() = component().buildColored().toRawMessage()

}