@file:Suppress("PropertyName", "SpellCheckingInspection")

import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.StandardOpenOption

val exposedVersion: String by project

plugins {
    id("com.github.johnrengelman.shadow")
}

taboolib {
    description {
        name(rootProject.name)
        prefix(rootProject.name)
        contributors {
            name("HiUsers")
        }
        links {
            name("homepage").url("https://iplugin.hiusers.com/")
        }
    }

    classifier = null
}


dependencies {
    rootProject.subprojects.filter { it.path.startsWith(":project:") }.forEach {
        if (it.name.contains("module-database")) {
            return@forEach
        }
        taboo(project(it.path)) { isTransitive = false }
    }

    configurations.implementation.get().dependencies.forEach {
        configurations.taboo.get().dependencies.add(it)
    }
}

tasks {
    jar {
        // 构件名
        archiveBaseName.set(rootProject.name)
    }
    shadowJar {
        val databaseProject = project(":project:module-database")
        dependsOn("${databaseProject.path}:shadowJar")
        dependsOn(jar)
        dependencies {
            exclude(dependency(".*:.*"))
        }
        from(jar)
        from(databaseProject.projectDir.resolve("build/libs/${databaseProject.name}-${rootProject.version}-all.jar")) {
            exclude("META-INF")
        }

        // 使用 spigot 的 libraries 载载入 kotlin 和exposed
        doLast {
            val finalJarFile = archiveFile.get().asFile
            val jarUri = URI.create("jar:${finalJarFile.toURI()}")
            FileSystems.newFileSystem(jarUri, emptyMap<String, Any>()).use { fs ->
                val pluginYmlPath = fs.getPath("plugin.yml")
                if (Files.exists(pluginYmlPath)) {
                    Files.write(
                        pluginYmlPath,
                        """
                            
                            libraries:
                              - org.jetbrains.exposed:exposed-core:$exposedVersion
                              - org.jetbrains.exposed:exposed-dao:$exposedVersion
                              - org.jetbrains.exposed:exposed-jdbc:$exposedVersion
                              - org.jetbrains.exposed:exposed-java-time:$exposedVersion
                        """.trimIndent().toByteArray(Charsets.UTF_8),
                        StandardOpenOption.APPEND
                    )
                }
            }
        }

        archiveBaseName.set(rootProject.name)
    }
    sourcesJar {
        // 构件名
        archiveBaseName.set(rootProject.name)
        // 打包子项目源代码
        rootProject.subprojects.forEach { from(it.sourceSets["main"].allSource) }
    }
    build {
        dependsOn(shadowJar)
    }
}