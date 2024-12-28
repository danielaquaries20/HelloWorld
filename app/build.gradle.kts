plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.firebaseGms)
    alias(libs.plugins.firebaseCrashlytic)
    alias(libs.plugins.firebasePerf)
}

android {
    namespace = "com.daniel.helloworld"
    compileSdk = 34

    configurations.implementation{
        exclude(group = "com.intellij", module = "annotations")
    }

    defaultConfig {
        applicationId = "com.daniel.helloworld"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
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

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
            kotlinOptions.jvmTarget = jvmTarget
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.objecthunter)

    implementation(libs.daggerHilt)
    kapt(libs.daggerHiltCompiler)

    implementation(libs.fragmentKtx)
    implementation(libs.coreCrocodic)
    implementation(libs.imageSlider)
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}