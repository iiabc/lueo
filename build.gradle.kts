@file:Suppress("PropertyName", "SpellCheckingInspection")

import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.izzel.taboolib") version "2.0.23"
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    kotlin("jvm") version "2.1.0"
}

val exposedVersion: String by project

subprojects {
    apply<JavaPlugin>()
    apply(plugin = "io.izzel.taboolib")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    // TabooLib 配置
    taboolib {
        env {
            install(Basic, Bukkit, BukkitUtil)
            install(CommandHelper)
            install(BukkitHook)
            install(Kether, JavaScript)
        }
        version {
            taboolib = "6.2.3-7105e58f"
        }
        relocate("top.maplex.arim","${rootProject.group}.arim")
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

        implementation("top.maplex.arim:Arim:1.2.14")

        compileOnly("org.jetbrains.exposed:exposed-core:${exposedVersion}")
        compileOnly("org.jetbrains.exposed:exposed-dao:${exposedVersion}")
        compileOnly("org.jetbrains.exposed:exposed-jdbc:${exposedVersion}")
        compileOnly("org.jetbrains.exposed:exposed-java-time:${exposedVersion}")
        implementation("com.zaxxer:HikariCP:4.0.3")
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