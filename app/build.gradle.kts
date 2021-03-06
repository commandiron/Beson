plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
//    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xuse-experimental=kotlin.ExperimentalUnsignedTypes",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xuse-experimental=kotlinx.coroutines.InternalCoroutinesApi",
            "-Xuse-experimental=androidx.compose.animation.ExperimentalAnimationApi",
            "-Xuse-experimental=androidx.compose.ExperimentalComposeApi",
            "-Xuse-experimental=androidx.compose.material3.ExperimentalMaterial3Api",
            "-Xuse-experimental=androidx.compose.material.ExperimentalMaterialApi",
            "-Xuse-experimental=androidx.compose.runtime.ExperimentalComposeApi",
            "-Xuse-experimental=androidx.compose.ui.ExperimentalComposeUiApi",
            "-Xuse-experimental=coil.annotation.ExperimentalCoilApi",
            "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi",
            "-Xuse-experimental=com.google.accompanist.pager.ExperimentalPagerApi",
            "-Xuse-experimental=com.google.accompanist.permissions.ExperimentalPermissionsApi",
            "-Xuse-experimental=androidx.constraintlayout.compose.ExperimentalMotionApi",
            "-Xuse-experimental=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi",
        )
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/ASM")
    }
}

dependencies {
    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.runtime)
    implementation(Compose.foundation)
    implementation(Compose.navigation)
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.constraintLayout)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.splashScreen)
    implementation(AndroidX.constraintLayout)

    implementation(Google.material3)
    implementation(Google.materialIconsCore)
    implementation(Google.materialIconsExtended)
    implementation(Google.mapsCompose)
    implementation(Google.playServicesMaps)
    implementation(Google.playServicesLocation)

    implementation(Accompanist.systemUi)
    implementation(Accompanist.pager)
    implementation(Accompanist.pagerIndicators)
    implementation(Accompanist.permissions)
    implementation(Accompanist.navigationMaterial)
    implementation(Accompanist.placeholderMaterial)
    implementation(Accompanist.swipeRefresh)

    implementation(Coil.coil)
    implementation(Coil.compose)
    implementation(Coil.composeBase)

    implementation(Firebase.auth)
    implementation(Firebase.database)
    implementation(Firebase.playServicesAuth)
    implementation(Firebase.storage)

    debugImplementation(Compose.uiTooling)

    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)

    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.testRunner)
}