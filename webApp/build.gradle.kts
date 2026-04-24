plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "webApp.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        jsMain.dependencies {
            implementation(project(":shared"))
            // Explicit Compose coords (composeMultiplatform plugin not applied here to avoid
            // LifecycleBasePlugin duplicate-registration conflict on pure-JS KMP modules)
            val composeVer = libs.versions.composeMultiplatform.get()
            implementation("org.jetbrains.compose.runtime:runtime:$composeVer")
            implementation("org.jetbrains.compose.foundation:foundation:$composeVer")
            implementation("org.jetbrains.compose.material3:material3:$composeVer")
            implementation("org.jetbrains.compose.ui:ui:$composeVer")
            implementation(libs.decompose.core)
            implementation(libs.essenty.lifecycle)
            implementation(libs.koin.core)
        }
    }
}
