import com.android.build.api.dsl.ApplicationBuildFeatures

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "ntu.letanvinh.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "ntu.letanvinh.myapplication"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(group = "com.github.ismaeldivita", name = "chip-navigation-bar", version = "1.4.0")
    implementation(group = "androiddx.viewpaper2", name = "viewpaper2", version = "1.0.0")
    implementation(group = "com.github.bumptech.glide", name = "glide", version = "4.12.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}