pluginManagement {
  repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    google()
    gradlePluginPortal()
    mavenCentral()
  }

  plugins {
    kotlin("jvm").version(extra["kotlin.version"] as String)
    id("org.jetbrains.compose").version(extra["compose.version"] as String)
    id("org.jetbrains.kotlin.plugin.compose").version(extra["kotlin.version"] as String)
  }
}

dependencyResolutionManagement {
    repositories {
        maven("https://www.jetbrains.com/intellij-repository/releases")
        google()
        mavenCentral()
    }
}

rootProject.name = "cfd-repro-demo"
