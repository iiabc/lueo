package com.hiusers.mc.lueo.util

import com.google.gson.Gson
import com.hiusers.mc.lueo.serializer.GsonProvider
import taboolib.module.configuration.Configuration


/**
 * 从 YAML 文件反序列化到 Kotlin 对象
 */
fun <T> Configuration.deserialize(classifier: Class<T>, gson: Gson = GsonProvider.gson): T {
    val json = gson.toJson(this.toMap())
    return gson.fromJson(json, classifier)
}

/**
 * 从 YAML 文件的指定Key的Section反序列化到 Kotlin 对象
 */
fun <T> Configuration.deserialize(key: String, classifier: Class<T>, gson: Gson = GsonProvider.gson): T {
    val section = this.getConfigurationSection(key)
    val json = gson.toJson(section?.toMap())
    return gson.fromJson(json, classifier)
}

