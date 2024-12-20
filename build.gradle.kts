import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("dev.hydraulic.conveyor") version "1.12"
}

group = "de.stefan-oltmann"
version = "1.0.0"

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://jitpack.io") }
}

kotlin {

    jvmToolchain(22)
}

dependencies {

    /* Compose */
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.components.resources)

    /* Coroutines */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

    /* Metadata read & write */
    implementation("com.ashampoo:kim:0.20.1")

    /* VIPS */
    implementation("app.photofox.vips-ffm:vips-ffm-core:1.3.0")

    /* Conveyor API */
    implementation("dev.hydraulic.conveyor:conveyor-control:1.1")

    /* Conveyor */
    linuxAmd64(compose.desktop.linux_x64)
    macAmd64(compose.desktop.macos_x64)
    macAarch64(compose.desktop.macos_arm64)
    windowsAmd64(compose.desktop.windows_x64)
}

compose.desktop {

    application {

        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "🔧 Thumbnail Fixer 🔧"
            packageVersion = version.toString()
        }
    }
}

// region Work around temporary Compose bugs.
configurations.all {
    attributes {
        // https://github.com/JetBrains/compose-jb/issues/1404#issuecomment-1146894731
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}
// endregion
