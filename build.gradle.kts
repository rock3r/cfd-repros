import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
  id("org.jetbrains.kotlin.plugin.compose")
}

group = "dev.sebastiano"

version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  google()

  // TODO remove when JEWEL-821 is fixed
  maven("https://cache-redirector.jetbrains.com/intellij-dependencies")
}

val mySSet =
  project.extensions.getByName("sourceSets") as SourceSetContainer
val mainSourceSet = mySSet.named("main").get()

logger.lifecycle(mainSourceSet.kotlin.srcDirs.first().path)

dependencies {
  implementation(compose.desktop.currentOs) { exclude(group = "org.jetbrains.compose.material") }
  implementation("org.jetbrains.jewel:jewel-int-ui-standalone:0.28.0-252.15920")
  implementation("org.jetbrains.jewel:jewel-markdown-int-ui-standalone-styling:0.28.0-252.15920")

  // TODO remove these when JEWEL-821 is fixed
  implementation("org.jetbrains.jewel:jewel-foundation:0.28.0-252.15920")
  implementation("org.jetbrains.jewel:jewel-ui:0.28.0-252.15920")
  implementation("org.jetbrains.jewel:jewel-markdown-core:0.28.0-252.15920")
  // END

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
