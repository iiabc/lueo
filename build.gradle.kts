@file:Suppress("PropertyName", "SpellCheckingInspection")

import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.izzel.taboolib") version "2.0.23"
    kotlin("jvm") version "2.1.0"
}

val exposedVersion: String by project

subprojects {
    apply<JavaPlugin>()
    apply(plugin = "io.izzel.taboolib")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    // TabooLib 配置
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
        env {
            install(Basic, Bukkit, BukkitUtil)
            install(CommandHelper)
            install(BukkitHook)
            install(Kether, JavaScript)

            // 开启隔离类加载器（沙盒模式）
//            enableIsolatedClassloader = true
        }
        version {
            taboolib = "6.2.3-8cc2f66"
            // 跳过kotlin重定向
            skipKotlinRelocate = true
        }
        relocate("top.maplex.arim","com.hiusers.mc.arim")
        relocate("org.jetbrains.exposed", "${rootProject.group}.jetbrains.exposed")
        relocate("org.jetbrains.kotlin", "${rootProject.group}.jetbrains.kotlin")
        relocate("com.zaxxer.hikari", "${rootProject.group}.zaxxer.hikari")
    }

    // 全局仓库
    repositories {
        mavenCentral()
        maven("https://repo.hiusers.com/releases")
    }

    // 全局依赖
    dependencies {
        compileOnly("ink.ptms.core:v12004:12004:mapped")
        compileOnly("ink.ptms.core:v12004:12004:universal")

        compileOnly("com.google.code.gson:gson:2.8.7")

        taboo("top.maplex.arim:Arim:1.2.14")

        taboo("org.jetbrains.exposed:exposed-core:${exposedVersion}")
        taboo("org.jetbrains.exposed:exposed-dao:${exposedVersion}")
        taboo("org.jetbrains.exposed:exposed-jdbc:${exposedVersion}")
        taboo("org.jetbrains.exposed:exposed-java-time:${exposedVersion}")
        taboo("com.zaxxer:HikariCP:4.0.3")

        taboo("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
        taboo("org.jetbrains.kotlin:kotlin-reflect:2.1.0")
    }
    // 编译配置
    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjvm-default=all", "-Xextended-compiler-checks")
        }
    }

    tasks.test {
        useJUnitPlatform()
    }
}

gradle.buildFinished {
    buildDir.deleteRecursively()
}