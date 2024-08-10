plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("kotlin-kapt")
//    alias(libs.plugins.org.jetbrains.kotlin.kapt)
//    id("com.google.devtools.ksp")
}

android {
    namespace = "com.hashinology.calendarapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hashinology.calendarapp"
        minSdk = 25
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.room.common)
    val lifecycle_version = "2.8.1"
    val arch_version = "2.2.0"
    val nav_version = "2.3.5"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")

    // Room components
    implementation ("androidx.room:room-runtime:2.5.0")
//    ksp("androidx.room:room-compiler:2.4.0")
    kapt ("androidx.room:room-compiler:2.5.0")
    implementation ("androidx.room:room-ktx:2.5.0")

    // Navigation Components:
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    /*// Annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")*/

    implementation(libs.kotlinx.serialization.json)
}
//ksp {
//    arg("room.schemaLocation", "$projectDir/schemas")
//}