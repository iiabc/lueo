package com.hiusers.mc.lueo.reader

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration

object ConfigReader {

    @Config("config.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    @ConfigNode("settings.luck.max")
    var maxLuck: Int = 100

    @ConfigNode("settings.luck.default")
    var defaultLuck: Int = 0

    @ConfigNode("settings.luck.reset-time")
    var resetTime: String = ""

}