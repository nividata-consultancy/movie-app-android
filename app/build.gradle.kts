plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.nividata.movie_time"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.4"
        kotlinCompilerVersion = "1.4.32"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")

    implementation("com.google.android.material:material:1.4.0")
    implementation(
        "androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02"
    )

    implementation("androidx.compose.ui:ui:1.0.4")
    implementation("androidx.compose.material:material:1.0.4")
    implementation("androidx.compose.ui:ui-tooling:1.0.4")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0-rc01")

    implementation("androidx.compose.ui:ui-util:1.0.4")

    implementation("androidx.activity:activity-compose:1.4.0-rc01")

    implementation("androidx.palette:palette-ktx:1.0.0")

    implementation("androidx.paging:paging-compose:1.0.0-alpha14")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha10")

    // Hilt Compose Navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")

    kapt("com.google.dagger:hilt-android-compiler:2.39.1")
    implementation("com.google.dagger:hilt-android:2.39.1")

    implementation(project(":domain"))

    // Jetpack DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("com.valentinilk.shimmer:compose-shimmer:1.0.1")

    // JetPack Security
    implementation("androidx.security:security-crypto:1.1.0-alpha03")

    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("com.google.accompanist:accompanist-pager:0.18.0")

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.4")
}