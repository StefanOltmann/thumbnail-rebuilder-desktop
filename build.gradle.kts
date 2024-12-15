import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "de.stefan-oltmann"
version = "1.0.0"

repositories {
    mavenCentral()
    google()
}

kotlin {

    jvmToolchain(22)
}

dependencies {

    /* Compose */
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.components.resources)

    /* Metadata read & write */
    implementation("com.ashampoo:kim:0.20.1")

    /* VIPS */
    implementation("app.photofox.vips-ffm:vips-ffm-core:1.3.0")
}

compose.desktop {

    application {

        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Thumbnail Rebuilder"
            packageVersion = version.toString()
        }
    }
}
