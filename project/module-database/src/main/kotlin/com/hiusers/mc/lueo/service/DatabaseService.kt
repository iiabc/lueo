package com.hiusers.mc.lueo.service

import com.hiusers.mc.lueo.database.table.PlayerLuckTable
import com.hiusers.mc.lueo.reader.ConfigReader
import com.hiusers.mc.lueo.system.Loadable
import com.hiusers.mc.lueo.system.Register
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.ExposedConnectionImpl
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.function.console
import taboolib.common.platform.function.disablePlugin
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.lang.sendLang

/**
 * @author iiabc
 * @since 2025/6/1 21:45
 */
//@RuntimeDependencies(
//    RuntimeDependency(
//        "org.jetbrains.exposed:exposed-core:1.0.0-beta-2",
//        transitive = false,
//        relocate = ["!org.jetbrains.exposed", "org.jetbrains.exposed"]
//    ),
//    RuntimeDependency("org.jetbrains.exposed:exposed-dao:1.0.0-beta-2",
//        transitive = false,
//        relocate = ["!org.jetbrains.exposed", "org.jetbrains.exposed"]
//    ),
//    RuntimeDependency("org.jetbrains.exposed:exposed-jdbc:1.0.0-beta-2",
//        transitive = false,
//        relocate = ["!org.jetbrains.exposed", "org.jetbrains.exposed"]
//    ),
//    RuntimeDependency("org.jetbrains.exposed:exposed-java-time:1.0.0-beta-2",
//        transitive = false,
//        relocate = ["!org.jetbrains.exposed", "org.jetbrains.exposed"]
//    ),
//    RuntimeDependency(value = "org.jetbrains.kotlin:kotlin-reflect:2.1.0",
//        transitive = false,
//        relocate = ["!org.jetbrains.kotlin",  "org.jetbrains.kotlin"]
//        ),
//    RuntimeDependency(
//        value = "!com.zaxxer:HikariCP:4.0.3",
//        test = "com.zaxxer.hikari.HikariDataSource",
//        relocate = ["!com.zaxxer.hikari", "com.zaxxer.hikari"],
//        transitive = false
//    )
//)
//RuntimeDependency(
//value = "!org.slf4j:slf4j-api:2.0.8",
//test = "org.slf4j.LoggerFactory",
//relocate = ["!org.slf4j", "org.slf4j"],
//transitive = false
//),
//RuntimeDependency(
//value = "!com.zaxxer:HikariCP:4.0.3",
//test = "com.zaxxer.hikari.HikariDataSource",
//relocate = ["!com.zaxxer.hikari", "com.zaxxer.hikari", "!org.slf4j", "org.slf4j"],
//transitive = false
//)
@Register
object DatabaseService : Loadable {

    override val priority: Int
        get() = 1

    override fun load() {
        val databaseConfig = ConfigReader.config.getConfigurationSection("database") ?: return
        // 配置数据库连接池
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = buildJdbcUrl(databaseConfig)
            username = databaseConfig.getString("username", "root")
            password = databaseConfig.getString("password", "")

            // 连接池优化参数
            maximumPoolSize = databaseConfig.getInt("pool-size", 5)
            connectionTimeout = databaseConfig.getLong("timeout", 30000)
            idleTimeout = databaseConfig .getLong("idle-timeout", 600000)
            maxLifetime = databaseConfig .getLong("max-lifetime", 1800000)

            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
            addDataSourceProperty("useServerPrepStmts", "true")
        }

        try {
            val databaseSource = HikariDataSource(hikariConfig)
            Database.connect(databaseSource, connectionAutoRegistration = ExposedConnectionImpl())
            createTables()
            console().sendLang("success_connect_mysql")
        } catch (e: Exception) {
            console().sendLang("error_connect_mysql")
            disablePlugin()
            error(e.message?: "Unknow error database")
        }
    }

    private fun buildJdbcUrl(config: ConfigurationSection): String {
        val host = config.getString("host", "localhost")
        val port = config.getInt("port", 3306)
        val database = config.getString("database", "minecraft_db")
        val useSSL = config.getBoolean("use-ssl", false)
        val allowPublicKeyRetrieval = config.getBoolean("allow-public-key-retrieval", true)
        val useUnicode = config.getBoolean("use-unicode", true)
        val characterEncoding = config.getString("character-encoding", "UTF-8")
        val serverTimezone = config.getString("server-timezone", "Asia/Shanghai")

        return "jdbc:mysql://$host:$port/$database?useSSL=$useSSL&" +
                "allowPublicKeyRetrieval=$allowPublicKeyRetrieval&" +
                "useUnicode=$useUnicode&" +
                "characterEncoding=$characterEncoding&" +
                "serverTimezone=$serverTimezone"
    }

    /**
     * 自动创建数据库表
     */
    private fun createTables() {
        transaction {
            SchemaUtils.create(PlayerLuckTable)
        }
    }

}