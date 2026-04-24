plugins {
    // composeMultiplatform must come first so LifecycleBasePlugin registers 'clean'
    // in webApp before KotlinJsBrowserPlugin applies BasePlugin (Gradle 8.9 fix).
    // Root tasks.register("clean") was removed to allow NodeJsRootPlugin to own
    // the root 'clean' task — that is what unblocked this plugin order.
    alias(libs.plugins.composeMultiplatform)
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
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(libs.decompose.core)
            implementation(libs.essenty.lifecycle)
            implementation(libs.koin.core)
        }
    }
}
