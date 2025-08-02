taboolib { subproject = true }
plugins {
    id("com.github.johnrengelman.shadow")
}

taboolib {
    version {
        skipKotlinRelocate = true
    }
    subproject = true
}

tasks {
    shadowJar {
        dependencies {
            exclude(dependency(".*:.*"))
        }
        taboolib.relocation.filter { !it.key.contains("kotlin") }.forEach {
            relocate(it.key, it.value)
        }
    }
    build {
        dependsOn(shadowJar)
    }
}

dependencies {
    compileOnly(project(":project:module-reader"))
    compileOnly(project(":project:module-util"))
}
