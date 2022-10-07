import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization") version Versions.KOTLIN
}

android {
    namespace = ProjectConfig.appId
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

            val key: String = gradleLocalProperties(rootDir).getProperty("API_ACCESS_KEY")
            buildConfigField("String", "API_ACCESS_KEY", key)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = Versions.JAVA
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.constraintLayout)

    implementation(AndroidX.lifecycleRuntime)
    implementation(AndroidX.lifecycleViewModel)
    implementation(AndroidX.activityLifecycle)
    implementation(AndroidX.fragmentLifecycle)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitConverter)
    implementation(Retrofit.okhttpLogging)
    implementation(Retrofit.serializationConverter)

    implementation(Kotlin.kotlinSerialization)
    implementation(Kotlin.kotlinSerializationJson)

    implementation(Google.material)

    implementation(ThirdParty.lottieAnim)

    implementation(DaggerHilt.daggerHiltAndroid)
    kapt(DaggerHilt.daggerHiltCompiler)

    implementation(Room.runtime)
    implementation(Room.ktx)
    kapt(Room.compiler)

    implementation(DataStore.dataStore)

    implementation(Test.extJUnit)
    implementation(Test.testJUnit)
    implementation(Test.espressoCore)
}