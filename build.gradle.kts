import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
  id("org.jetbrains.kotlin.plugin.compose")
  id("org.jetbrains.intellij.platform.base") version "2.3.0"
}

group = "dev.sebastiano"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  maven("https://packages.jetbrains.team/maven/p/kpm/public/")
  google()

  intellijPlatform {
    defaultRepositories()
  }
}

dependencies {
  implementation(compose.desktop.currentOs) {
    exclude(group = "org.jetbrains.compose.material")
  }
  implementation("org.jetbrains.jewel:jewel-int-ui-standalone-242:0.27.0")

  intellijPlatform {
    intellijIdeaCommunity("2024.3.1")
  }
  testImplementation(compose.desktop.uiTestJUnit4)
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "cfd-repro-demo"
      packageVersion = "1.0.0"
    }
  }
}
