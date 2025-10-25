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
}

val mySSet = project.extensions.getByName("sourceSets") as SourceSetContainer
val mainSourceSet = mySSet.named("main").get()

logger.lifecycle(mainSourceSet.kotlin.srcDirs.first().path)

dependencies {
  implementation(compose.desktop.currentOs) { exclude(group = "org.jetbrains.compose.material") }

    val jewelVersion = "0.31.0-252.27409"
    implementation("org.jetbrains.jewel:jewel-int-ui-standalone:$jewelVersion")
    implementation("org.jetbrains.jewel:jewel-markdown-int-ui-standalone-styling:$jewelVersion")

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
