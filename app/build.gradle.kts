plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // ✅ estos son necesarios explícitamente para Room con KAPT
    id("kotlin-kapt")
}

android {
    namespace = "com.elcoxie.agendamientosaludprimeroapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.elcoxie.agendamientosaludprimeroapp"
        minSdk = 24
        targetSdk = 36
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

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}


    dependencies {

        // jetpack compose
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)
        implementation("androidx.compose.material:material-icons-extended")
        implementation("androidx.compose.material:material")
        implementation("androidx.navigation:navigation-compose:2.8.0")
// Material Components (provee Theme.Material3.* en XML)
        implementation("com.google.android.material:material:1.12.0")

// compose
        implementation("androidx.activity:activity-compose:1.9.3")
        implementation(platform("androidx.compose:compose-bom:2025.01.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.navigation:navigation-compose:2.8.4")

        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

        // sql room
        implementation("androidx.room:room-runtime:2.6.1")
        implementation("androidx.room:room-ktx:2.6.1")
        kapt("androidx.room:room-compiler:2.6.1")


        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)
        debugImplementation(libs.androidx.compose.ui.tooling)
        debugImplementation(libs.androidx.compose.ui.test.manifest)
    }

