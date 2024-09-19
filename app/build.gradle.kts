plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.learningapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.learningapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["appAuthRedirectScheme"] = "com.example.learningapp"

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
    }

}
object Libs {
    object Plugins {
        val android = "com.android.application"
        val kotlin = "org.jetbrains.kotlin.android"
        val kotlinKapt = "org.jetbrains.kotlin.kapt"
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.work.runtime.ktx)

    //Firebase
    implementation(platform(libs.firebase.bom.v3120))
    implementation (libs.firebase.messaging)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.ktx)
    implementation (libs.firebase.storage)

    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.vision.common)
    implementation(libs.play.services.mlkit.face.detection)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.dotsindicator)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation (libs.androidx.lifecycle.runtime)
    implementation (libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation (libs.moshi)
    kapt ("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
    implementation (libs.moshi)
    implementation (libs.moshi.kotlin)
    kapt (libs.com.squareup.moshi.moshi.kotlin.codegen)
    implementation (libs.sdp.android)
    implementation (libs.ssp.android)
    implementation (libs.itextpdf)
    implementation (libs.shimmer)
    implementation(libs.lottie)
    implementation (libs.firebase.auth.v2200)
    implementation (libs.play.services.auth)
    implementation(platform(libs.firebase.bom))

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle.v133)
    implementation(libs.androidx.camera.view.v133)
    implementation(libs.androidx.camera.extensions)
    implementation (libs.guava)

    implementation(libs.play.services.mlkit.text.recognition.common)
    implementation(libs.play.services.mlkit.text.recognition)
    implementation(libs.balloon)
    implementation(libs.play.services.ads)
    implementation (libs.appauth)

    implementation (libs.converter.scalars)

    //WebSocket Library
    implementation (libs.okhttp)

    //Glide Library to show Images
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation (libs.hilt.android)
    kapt (libs.hilt.android.compiler)

}

