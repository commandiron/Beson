object Build {
    private const val androidBuildToolsVersion = "7.1.0"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    private const val kotlinVersion = "1.6.10"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    private const val hiltAndroidGradlePluginVersion = "2.38.1"
    const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltAndroidGradlePluginVersion"

    private const val googleServicesVersion = "4.3.10"
    const val googleServices = "com.google.gms:google-services:$googleServicesVersion"

    private const val secretsGradleVersion = "2.0.1"
    const val secretsGradle= "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:$secretsGradleVersion"
}
