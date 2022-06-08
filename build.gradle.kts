buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltAndroidGradlePlugin)
        classpath(Build.googleServices)
//        classpath(Build.secretsGradle)
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}