plugins {
    id("com.android.application") version Versions.GRADLE apply false
    id("com.android.library") version Versions.GRADLE apply false
    id("org.jetbrains.kotlin.android") version Versions.KOTLIN apply false
    id("com.google.dagger.hilt.android") version Versions.HILT apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
