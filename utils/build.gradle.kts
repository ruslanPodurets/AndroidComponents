plugins {
    id(Plugins.AndroidLibrary)
    id(Plugins.JetBrainsKotlinAndroid)
}

android {
    namespace = "lib.utils"
    compileSdk = Configs.compileSdk

    defaultConfig {
        minSdk = Configs.minSdk
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Configs.Lang.sourceCompatibility()
        targetCompatibility = Configs.Lang.targetCompatibility()
    }
    kotlinOptions {
        jvmTarget = Configs.Lang.jvmTarget
    }
}

dependencies {
    implementation(Libs.AndroidXCoreKtx)
    implementation(Libs.Appcompat)
    implementation(Libs.Material)
}